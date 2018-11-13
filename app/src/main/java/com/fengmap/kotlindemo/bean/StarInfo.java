package com.fengmap.kotlindemo.bean;

import java.io.Serializable;

/**
 *
 * Created by bai on 2018/11/13.
 */

public class StarInfo implements Serializable{

    private String all;
    private String color;
    private String health;
    private String love;
    private String money;
    private int number;
    private String QFriend;
    private String summary;
    private String work;
    private String info;
    private String text;
    private String career;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getCareerfinance() {
        return careerfinance;
    }

    public void setCareerfinance(String careerfinance) {
        this.careerfinance = careerfinance;
    }

    private String careerfinance;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQFriend() {
        return QFriend;
    }

    public void setQFriend(String QFriend) {
        this.QFriend = QFriend;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "StarInfo{" +
                "all='" + all + '\'' +
                ", color='" + color + '\'' +
                ", health='" + health + '\'' +
                ", love='" + love + '\'' +
                ", money='" + money + '\'' +
                ", number=" + number +
                ", QFriend='" + QFriend + '\'' +
                ", summary='" + summary + '\'' +
                ", work='" + work + '\'' +
                ", info='" + info + '\'' +
                ", text='" + text + '\'' +
                ", career='" + career + '\'' +
                ", careerfinance='" + careerfinance + '\'' +
                '}';
    }
}
