package com.xiao.consistency;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Shard<S> { // S类封装了机器节点的信息 ，如name、password、ip、port等

    private TreeMap<Long, S> nodes; // 虚拟节点
    private List<S> shards; // 真实机器节点
    private final int NODE_NUM = 100; // 每个机器节点关联的虚拟节点个数

    public Shard(List<S> shards) {
        super();
        this.shards = shards;
        init();
    }

    public List<S> getShards() {
        return shards;
    }

    public void setShards(List<S> shards) {
        this.shards = shards;
    }

    private void init() { // 初始化一致性hash环
        nodes = new TreeMap<Long, S>();
        for (int i = 0; i != shards.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
            final S shardInfo = shards.get(i);

            for (int n = 0; n < NODE_NUM; n++) {
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                Long hashCode = hash("SHARD-" + i + "-NODE-" + n);
                System.out.println("hashcode----->"+hashCode);
                nodes.put(hashCode, shardInfo);
            }

            System.out.println();

        }
    }

    //返回对应的真实节点
    public S getShardInfo(String key) {
        SortedMap<Long, S> tail = nodes.tailMap(hash(key)); // 沿环的顺时针找到一个虚拟节点
        if (tail.size() == 0) {
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
    }

    /**
     *  MurMurHash算法，是非加密HASH算法，性能很高，
     *  比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     *  http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {

        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     */
    private static int getHash(String str)
    {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    public static void main(String[] args){

        Node node1 = new Node("redis服务器1","192.168.0.1", 4040);
        Node node2 = new Node("redis服务器1","192.168.0.1", 4041);
        Node node3 = new Node("redis服务器1","192.168.0.1", 4042);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        Shard<Node> shard = new Shard<Node>(nodes);

        String[] keys = {"there", "is", "now", "ganji"};
        for(String key : keys)
            System.out.println("key:"+key+"->"+shard.getShardInfo(key));

        //删除一个节点
        shard.getShards().remove(node1);
        shard.init();
        System.out.println("\n-------------------------------------------");
        System.out.println("删除节点");
        System.out.println("-------------------------------------------\n");

        for(String key : keys)
            System.out.println("key:"+key+"->"+shard.getShardInfo(key));

        //增加一个节点
        shard.getShards().add(node1);
        shard.init();
        System.out.println("\n-------------------------------------------");
        System.out.println("增加节点");
        System.out.println("-------------------------------------------\n");

        for(String key : keys)
            System.out.println("key:"+key+"->"+shard.getShardInfo(key));

    }

}

