package com.hhh.temp.bean;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by huanghh on 2017/5/16.
 */
public class Order extends BaseBean implements Serializable{
    public final static Class thisC = Order.class;
    public final static String thisName = thisC.getSimpleName();

    public static void main(String[] args) {
        System.out.println("Order");
    }

    private int orderAmount;
    private String address;
    private long date;

    public Order(int orderAmount, String address) {
        this(0,orderAmount,address);
    }
    public Order(int id,int orderAmount, String address) {
        super.id = id;
        this.orderAmount = orderAmount;
        this.address = address;
        date = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this,this.getClass());
    }

    public int getOrderAmount() {
        if(orderAmount==0){
            orderAmount = 1;
        }
        return orderAmount;
    }

    public String getAddress() {
        return address;
    }

    public long getDate() {
        return date;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
