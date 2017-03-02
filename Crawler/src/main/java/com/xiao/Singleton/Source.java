package com.xiao.Singleton;

import com.xiao.Base;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;


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

    private volatile Source source;

    //对相应的方法进行加锁操作
    ReentrantLock lock = new ReentrantLock();

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

    public Source getInstance() {
        if (null == source) {
            synchronized (Source.class) {
                if (null == source) {
                    source = new Source();
                    init();
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
     */
    public boolean putAndGetStatus(String key, Object o) {
        lock.lock();
        Boolean flag = new Boolean(false);
        SortedMap<String, Object> map = getShardInfo(key);
        if (!map.containsKey(key)) {
            map.put(key, o);
            flag = true;
        }
        lock.unlock();
        return flag;
    }

}
