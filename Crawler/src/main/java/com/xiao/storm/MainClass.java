package com.xiao.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by xiaojie on 17/5/20.
 */

public class MainClass {

    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        //创建一个TopologyBuilder
        TopologyBuilder tb = new TopologyBuilder();
        tb.setSpout("SpoutBolt", new SpoutBolt(), 2);
        tb.setBolt("SplitBolt", new SplitBolt(), 2).shuffleGrouping("SpoutBolt");
        tb.setBolt("CountBolt", new CountBolt(), 4).fieldsGrouping("SplitBolt", new Fields("word"));
        //创建配置
        Config conf = new Config();
        //设置worker数量
        conf.setNumWorkers(2);

        /* 提交任务的方式有本地模式和集群模式 */

        /* 集群提交方式 */
        //StormSubmitter.submitTopology("myWordcount", conf, tb.createTopology());

        /* 本地提交方式 */
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("wordCount", conf, tb.createTopology());

    }

}

