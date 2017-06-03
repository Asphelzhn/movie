package com.xiao.jstorm;


import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.Map;

/**
 * Created by xiaojie on 17/5/20.
 */
public class SpoutBolt extends BaseRichSpout {

    SpoutOutputCollector collector;

    /**
     * 初始化方法
     */
    public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    /**
     * 重复调用方法
     */
    public void nextTuple() {
        collector.emit(new Values("hello world this is a test"));
    }

    /**
     * 输出
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("test"));
    }

}
