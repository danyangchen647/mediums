package com.cdy.example.entity;

/**
 * Created by 星尘 on 2024/1/6.
 */
public class Information {
    private String ref; // 前10篇文章的key
    private String clap; // 点赞数量
    private String url; // 每篇文章的url
    private String context; // 内容
    private String title; // 标题
    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getClap() {
        return clap;
    }

    public void setClap(String clap) {
        this.clap = clap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
