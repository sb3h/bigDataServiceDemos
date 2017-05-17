package com.hhh.temp;

import com.google.gson.Gson;
import com.hhh.temp.bean.Order;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.Random;

import static com.hhh.temp.tools.RandomTools.*;

/**
 * Created by huanghh on 2017/5/16.
 */
public class HelloJava {
    public static void main(String[] args) {
        System.out.println("com.hhh.temp.HelloJava");

        String[] addresss = {"广州", "深圳", "珠海", "汕头", "韶关", "佛山", "江门", "湛江", "茂名", "肇庆", "梅洲", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮" };

        int minI = 500;
        int maxI = 1000;

        String address = "";

        for (int i = 0; i < 10; i++) {
            int randomI = getRandomI(minI, maxI);
            System.out.println(randomI);
            address = addresss[getRandomI(addresss.length)];
            System.out.println(address);
            Order order = new Order(i, randomI, address);
//            System.out.println(order);
            sendToKafka(order);
        }

    }

    private static void sendToKafka(Order order) {
//        //设置配置文件
        String TOPIC = "myTopic";
        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.101.121:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        props.put("request.required.acks", "1");
        //创建Producer
        Producer<Integer, String> producer = new Producer<Integer, String>(new ProducerConfig(props));
        //发送数据
        producer.send(new KeyedMessage<Integer, String>(TOPIC, "Message_"+order.toString()));
    }

}
