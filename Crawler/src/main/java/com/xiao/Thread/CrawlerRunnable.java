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

            List<String> urls = new ArrayList<String>();

            String domain = url.split("/")[2];
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.getElementsByTag("a");
            if (null != links && links.size() > 0) {
                for (Element link : links) {
                    String linkHref = link.attr("href");
                    String linkText = link.text();

                    if (linkHref.endsWith("html")) {

                        if (!linkHref.startsWith("http"))
                            linkHref = "http://" + domain + "/" + linkHref;

                        if(linkHref.contains(domain)) {
                            if (source.putAndGetStatus(linkHref, linkText)) {
                                System.out.println(Thread.currentThread().getName() + "------>" +linkHref +"----->" + doc.text());
                                urls.add(linkHref);
                            }
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
