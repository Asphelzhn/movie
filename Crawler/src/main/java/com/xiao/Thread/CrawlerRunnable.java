package com.xiao.Thread;

import com.xiao.Singleton.Source;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class CrawlerRunnable implements Runnable {

    private String url;
    private CountDownLatch countDownLatch;
    private Source source;

    public CrawlerRunnable(String url, CountDownLatch countDownLatch, Source source) {
        this.url = url;
        this.countDownLatch = countDownLatch;
        this.source = source;
    }

    @Override
    public void run() {
        try {

            Document doc = Jsoup.connect(url).timeout(20000).get();
            String text = doc.text();
            //判断是否已经访问过了
            if(!source.putAndGetStatus(url, text)){
                return;
            }

            System.out.println(Thread.currentThread().getName() + "------>" + url +"----->" + doc.text());

            List<String> urls = new ArrayList<String>();

            String domain = url.split("/")[2];
            Elements links = doc.getElementsByTag("a");
            if (null != links && links.size() > 0) {
                for (Element link : links) {
                    String linkHref = link.attr("href");

                    if (linkHref.endsWith("html")) {

                        if (!linkHref.startsWith("http")) {
                            linkHref =  domain + "/" + linkHref;
                            linkHref = "http://" + linkHref.replaceAll("//", "/");
                        }

                        if(linkHref.contains(domain)) {
                                urls.add(linkHref);
                        }
                    }

                }
            }

            if (!urls.isEmpty()) {
                CountDownLatch countDownLatchThread = new CountDownLatch(urls.size());
                final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                for (String s : urls) {
                    fixedThreadPool.submit(new CrawlerRunnable(s, countDownLatchThread, source));
                }
                countDownLatchThread.await();
                fixedThreadPool.shutdown();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }

    }

}
