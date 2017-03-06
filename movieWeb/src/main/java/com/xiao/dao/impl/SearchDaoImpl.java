package com.xiao.dao.impl;

import com.xiao.bean.Movie;
import com.xiao.dao.SearchDao;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaojie on 17/3/5.
 */
@Repository
public class SearchDaoImpl implements SearchDao{

    private final String path = "/Users/xiaojie/lucene";
    private final int number = 10;//默认检索结果数

    public List<Movie> search(String keyWord) {

        List<Movie> movies = new ArrayList<Movie>();

        //判断是否检索内容是否为空
        if (StringUtils.isBlank(keyWord))
            return movies;

        try {
            Directory directory = FSDirectory.open(Paths.get(path));

            //创建分词器
            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexReader ir = DirectoryReader.open(directory);

            // 搜索器
            IndexSearcher searcher = new IndexSearcher(ir);

            // 查询哪个字段
            QueryParser parse = new QueryParser("content", analyzer);
            Query query = parse.parse(keyWord);
            TopDocs topDocs = searcher.search(query, number);

            // 碰撞结果
            ScoreDoc[] hits = topDocs.scoreDocs;
            if(null != hits) {
                for (int i = 0; i < hits.length; i++) {
                    ScoreDoc hit = hits[i];
                    Document hitDoc = searcher.doc(hit.doc);
                    Movie movie = new Movie();
                    String content = hitDoc.get("content");
                    if(null != content && content.length() > 60) {
                        movie.setContent(content.substring(0, 60));
                        movie.setTitle(content.substring(0, 25));
                    }else{
                        movie.setContent(content);
                        movie.setTitle(content);
                    }
                    movie.setUrl(hitDoc.get("url"));
                    movies.add(movie);
                    System.out.println(hit.score + " " + hitDoc.get("url"));
                    System.out.println(hitDoc.get("content"));
                    System.out.println("---------------------------------");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }finally {
            return movies;
        }
    }
}
