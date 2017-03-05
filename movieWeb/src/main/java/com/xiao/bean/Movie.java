package com.xiao.bean;

import java.io.Serializable;

/**
 * Created by xiaojie on 17/3/5.
 */
public class Movie implements Serializable{

    private static final long serialVersionUID = 1L;//序列化版本信息
    private String url;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
