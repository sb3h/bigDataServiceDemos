package com.hhh.temp;

import com.google.gson.Gson;
import com.hhh.temp.bean.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huanghh on 2017/5/18.
 */
public class HelloJsonToOrder {
    public static void main(String[] args) {
        String json = "{\"id\":9,\"orderAmount\":737,\"address\":\"东莞\",\"date\":1495094289510}";
        Class<Order> clazz = Order.class;
        Gson gson = new Gson();
        Order order = gson.fromJson(json, clazz);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String key = String.format("%s_%s",order.getAddress(),sdf.format(new Date(order.getDate())));
        int value = order.getOrderAmount();

        System.out.println(key);
        System.out.println(value);
    }
}
