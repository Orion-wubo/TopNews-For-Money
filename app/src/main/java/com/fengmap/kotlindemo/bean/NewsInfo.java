package com.fengmap.kotlindemo.bean;

import java.io.Serializable;

/**
 * Created by bai on 2018/11/9.
 */

public class NewsInfo implements Serializable {
   private String title ;
   private String date;
   private String url;
   private String thumbnail_pic_s;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                '}';
    }
}
