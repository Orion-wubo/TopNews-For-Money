package com.fengmap.kotlindemo.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by bai on 2018/11/9.
 */

@Entity
public class NewsInfo  implements Serializable {
    @Id(autoincrement = true)
    private Long id;

    private String title;
    private String date;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s3;
    private String thumbnail_pic_s2;
    private boolean isChoose = false;

    @Generated(hash = 166641249)
    public NewsInfo(Long id, String title, String date, String url,
            String thumbnail_pic_s, String thumbnail_pic_s3,
            String thumbnail_pic_s2, boolean isChoose) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.url = url;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.thumbnail_pic_s3 = thumbnail_pic_s3;
        this.thumbnail_pic_s2 = thumbnail_pic_s2;
        this.isChoose = isChoose;
    }

    @Generated(hash = 859431180)
    public NewsInfo() {
    }

    public String getThumbnail_pic_s3() {
        return thumbnail_pic_s3;
    }

    public void setThumbnail_pic_s3(String thumbnail_pic_s3) {
        this.thumbnail_pic_s3 = thumbnail_pic_s3;
    }

    public String getThumbnail_pic_s2() {
        return thumbnail_pic_s2;
    }

    public void setThumbnail_pic_s2(String thumbnail_pic_s2) {
        this.thumbnail_pic_s2 = thumbnail_pic_s2;
    }

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
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                ", thumbnail_pic_s3='" + thumbnail_pic_s3 + '\'' +
                ", thumbnail_pic_s2='" + thumbnail_pic_s2 + '\'' +
                '}';
    }

    public boolean getIsChoose() {
        return this.isChoose;
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
