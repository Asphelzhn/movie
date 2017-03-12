package com.xiao.Singleton;

import com.xiao.bean.User;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by Administrator on 2017/3/2 0002.
 * 用于获取相应的资源
 */
public class Source extends Base {

    //存储爬取信息的容器
    private SortedMap<String, User> map1 = new TreeMap<String, User>();
    private SortedMap<String, User> map2 = new TreeMap<String, User>();
    private SortedMap<String, User> map3 = new TreeMap<String, User>();

    private TreeMap<Long, SortedMap<String, User>> nodes; // 虚拟节点
    private List<SortedMap<String, User>> shards; // 真实机器节点
    private final int NODE_NUM = 100; // 每个机器节点关联的虚拟节点个数

    private volatile static Source source;
    private final String path = "/Users/xiaojie/lucene";
    private final int number = 100;//默认检索结果数

    //对map容器存放值进行加锁操作
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    /**
     * 初始化相应的数据结构
     */
    private void init() {
        //添加真实节点
        shards = new LinkedList<SortedMap<String, User>>();
        shards.add(map1);
        shards.add(map2);
        shards.add(map3);

        nodes = new TreeMap<Long, SortedMap<String, User>>();
        for (int i = 0; i != shards.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
            final SortedMap<String, User> shardInfo = shards.get(i);
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
    public SortedMap<String, User> getShardInfo(String key) {
        SortedMap<Long, SortedMap<String, User>> tail = nodes.tailMap(hash(key)); // 沿环的顺时针找到一个虚拟节点
        if (tail.size() == 0) {
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
    }

    /**
     * 使用分布式一致性hash算法做负载 存储用户统计的状态信息
     *
     * @param userId
     * @param url
     * @return
     */
    public void put(String userId, String url) {
        lock.writeLock().lock();
        SortedMap<String, User> map = getShardInfo(userId);
        User user = new User(userId);
        if (map.containsKey(userId)) {
            user = map.get(userId);
        }

        SortedMap<String, Long> recordMap = user.getRecord();
        Long times = 0L;
        if (recordMap.containsKey(url)) {
            times = recordMap.get(url);
        }
        times++;
        recordMap.put(url, times);
        if(!map.containsKey(userId))
            map.put(userId, user);
        lock.writeLock().unlock();
    }

    /**
     * 获取map中存取的数据
     * @param userId
     * */
    public User get(String userId){
        lock.readLock().lock();
        SortedMap<String, User> map = getShardInfo(userId);
        User user = map.get(userId);
        lock.readLock().unlock();
        return user;
    }

}
