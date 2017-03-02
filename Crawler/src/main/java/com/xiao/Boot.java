package com.xiao;

import com.xiao.Singleton.Source;
import com.xiao.Thread.CrawlerRunnable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class Boot {

    public static void main(String[] args) {

        final String[] urls = {"http://www.dytt8.net/index.htm", "http://www.dy2018.com", "http://www.dygang.com"};
        final CountDownLatch countDownLatch = new CountDownLatch(urls.length);
        final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        final Source source = Source.getInstance();

        for(String url : urls){
            fixedThreadPool.submit(new CrawlerRunnable(url, countDownLatch, source));
        }

        try {
            countDownLatch.await();
            fixedThreadPool.shutdown();
            source.print();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
