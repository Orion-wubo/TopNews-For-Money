package com.fengmap.kotlindemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bai on 2018/11/13.
 */

public class DateInfo implements Serializable {
    /**
     * symbol : 00049L
     * sname : 财通纯债分级债券
     * per_nav : -
     * total_nav : -
     * yesterday_nav : -
     * nav_rate : -
     * nav_a : -
     * sg_states : 开放
     * nav_date : 0000-00-00
     * jjlx : 债券型基金
     */

    private String symbol;
    private String sname;
    private String per_nav;
    private String total_nav;
    private String yesterday_nav;
    private String nav_rate;
    private String nav_a;
    private String sg_states;
    private String nav_date;
    private String jjlx;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPer_nav() {
        return per_nav;
    }

    public void setPer_nav(String per_nav) {
        this.per_nav = per_nav;
    }

    public String getTotal_nav() {
        return total_nav;
    }

    public void setTotal_nav(String total_nav) {
        this.total_nav = total_nav;
    }

    public String getYesterday_nav() {
        return yesterday_nav;
    }

    public void setYesterday_nav(String yesterday_nav) {
        this.yesterday_nav = yesterday_nav;
    }

    public String getNav_rate() {
        return nav_rate;
    }

    public void setNav_rate(String nav_rate) {
        this.nav_rate = nav_rate;
    }

    public String getNav_a() {
        return nav_a;
    }

    public void setNav_a(String nav_a) {
        this.nav_a = nav_a;
    }

    public String getSg_states() {
        return sg_states;
    }

    public void setSg_states(String sg_states) {
        this.sg_states = sg_states;
    }

    public String getNav_date() {
        return nav_date;
    }

    public void setNav_date(String nav_date) {
        this.nav_date = nav_date;
    }

    public String getJjlx() {
        return jjlx;
    }

    public void setJjlx(String jjlx) {
        this.jjlx = jjlx;
    }
}

