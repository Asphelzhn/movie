package com.xiao.Singleton;

import com.xiao.Base;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by Administrator on 2017/3/2 0002.
 * 用于获取相应的资源
 */
public class Source extends Base {

    //存储爬取信息的容器
    private SortedMap<String, Object> map1 = new TreeMap<String, Object>();
    private SortedMap<String, Object> map2 = new TreeMap<String, Object>();
    private SortedMap<String, Object> map3 = new TreeMap<String, Object>();

    private TreeMap<Long, SortedMap<String, Object>> nodes; // 虚拟节点
    private List<SortedMap<String, Object>> shards; // 真实机器节点
    private final int NODE_NUM = 100; // 每个机器节点关联的虚拟节点个数

    private volatile static Source source;
    private final String path = "/Users/xiaojie/lucene";
    private final int number = 1000;//默认检索结果数

    //对map容器存放值进行加锁操作
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //对建立索引进行枷锁操作
    ReentrantLock indexSearchLock = new ReentrantLock();


    /**
     * 初始化相应的数据结构
     */
    private void init() {
        //添加真实节点
        shards = new LinkedList<SortedMap<String, Object>>();
        shards.add(map1);
        shards.add(map2);
        shards.add(map3);

        nodes = new TreeMap<Long, SortedMap<String, Object>>();
        for (int i = 0; i != shards.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
            final SortedMap<String, Object> shardInfo = shards.get(i);
            for (int n = 0; n < NODE_NUM; n++) {
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                Long hashCode = hash("SHARD-" + i + "-NODE-" + n);
                nodes.put(hashCode, shardInfo);
            }
        }
    }

    private Source() {
    }

    public static Source getInstance() {
        if (null == source) {
            synchronized (Source.class) {
                if (null == source) {
                    source = new Source();
                    source.init();
                }
            }
        }
        return source;
    }

    /**
     * 返回对应的真实节点
     *
     * @param key
     * @return
     */
    public SortedMap<String, Object> getShardInfo(String key) {
        SortedMap<Long, SortedMap<String, Object>> tail = nodes.tailMap(hash(key)); // 沿环的顺时针找到一个虚拟节点
        if (tail.size() == 0) {
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
    }

    /**
     * 使用分布式一致性hash算法做负载 存储爬取信息
     *
     * @param key
     * @param o
     * @return
     */
    public boolean putAndGetStatus(String key, Object o) {
        lock.writeLock().lock();
        Boolean flag = new Boolean(false);
        SortedMap<String, Object> map = getShardInfo(key);
        if (!map.containsKey(key)) {
            map.put(key, o);
            flag = true;
        }
        lock.writeLock().unlock();
        return flag;
    }

    //对所有数据进行索引
    public void index() {
        lock.readLock().lock();
        for (SortedMap<String, Object> map : shards) {
            System.out.println("start index map size:" + map.size());
            index(path, map);
            System.out.println("finish index map");
        }
        lock.readLock().unlock();
    }

    /**
     * 建立索引
     *
     * @param path
     * @param map
     */
    public void index(String path, SortedMap<String, Object> map) {
        //进行加锁操作
        indexSearchLock.lock();
        try {
            Directory directory = FSDirectory.open(Paths.get(path));
            //创建分词器
            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            for (SortedMap.Entry<String, Object> entry : map.entrySet()) {
                String url = entry.getKey();
                String content = (String) entry.getValue();
                Document doc = new Document();
                doc.add(new StringField("url", url, Field.Store.YES));
                //对于内容只索引并且存储
                doc.add(new TextField("content", content, Field.Store.YES));
                indexWriter.addDocument(doc);
            }
            indexWriter.close();
            directory.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            indexSearchLock.unlock();
        }
    }

    /**
     * 检索索引
     *
     * @param searchWord
     */
    public void search(String searchWord) {

        //判断是否检索内容是否为空
        if (StringUtils.isBlank(searchWord))
            return;

        try {
            Directory directory = FSDirectory.open(Paths.get(path));

            //创建分词器
            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexReader ir = DirectoryReader.open(directory);

            // 搜索器
            IndexSearcher searcher = new IndexSearcher(ir);

            // 查询哪个字段
            QueryParser parse = new QueryParser("content", analyzer);
            Query query = parse.parse(searchWord);
            TopDocs topDocs = searcher.search(query, number);

            // 碰撞结果
            ScoreDoc[] hits = topDocs.scoreDocs;
            if(null != hits) {
                for (int i = 0; i < hits.length; i++) {
                    ScoreDoc hit = hits[i];
                    Document hitDoc = searcher.doc(hit.doc);
                    System.out.println(hit.score + " " + hitDoc.get("url"));
                    System.out.println(hitDoc.get("content"));
                    System.out.println("---------------------------------");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

    }


}
