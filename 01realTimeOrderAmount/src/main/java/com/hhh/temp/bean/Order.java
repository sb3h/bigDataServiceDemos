package com.hhh.temp.bean;

import com.google.gson.Gson;

/**
 * Created by huanghh on 2017/5/16.
 */
public class Order {
    private int id;
    private int orderAmount;
    private String address;
    private long date;

    public Order(int id,int orderAmount, String address) {
        this.id = id;
        this.orderAmount = orderAmount;
        this.address = address;
        date = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this,this.getClass());
    }
}
