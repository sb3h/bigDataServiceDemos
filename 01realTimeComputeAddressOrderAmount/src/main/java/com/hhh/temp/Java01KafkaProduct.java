package com.hhh.temp;

import com.google.gson.Gson;
import com.hhh.temp.bean.Order;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

import static com.hhh.temp.tools.RandomTools.*;

/**
 * Created by huanghh on 2017/5/16.
 */
public class Java01KafkaProduct {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("com.hhh.temp.Java01KafkaProduct");

        String[] addresss = {"广州", "深圳", "珠海", "汕头", "韶关", "佛山", "江门", "湛江", "茂名", "肇庆", "梅洲", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮" };

        int minI = 500;
        int maxI = 1000;

        String address = "";
        int randomI = 0;
        Producer<Integer, String> producer = initKafkaProducer();
        for (int i = 0; i < 100; i++) {
//            randomI = getRandomI(minI, maxI);
            address = addresss[getRandomI(addresss.length)];
            //为了测试程序是否正确，特意写这样
            randomI = 1;
//            address = addresss[0];

            Order order = new Order(i, randomI, address);
//            System.out.println(order);
            Thread.sleep(10);
            System.out.println(order);
            sendToKafka(order,producer);
        }
        producer.close();

    }


    private static Producer<Integer, String> initKafkaProducer() {
        //设置配置文件
        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.101.121:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("serializer.encoding", "UTF-8");
//        props.put("request.required.acks", "1");
        //创建Producer

        Producer<Integer, String> producer = new Producer<Integer, String>(new ProducerConfig(props));

        return producer;
    }

    private static void sendToKafka(Order order, Producer<Integer, String> producer) {
        String TOPIC = "myTopic";
        //发送数据
//        producer.send(new KeyedMessage<Integer, String>(TOPIC, "Message_"+order.toString()));//暂时使用没有格式的数据
        producer.send(new KeyedMessage<Integer, String>(TOPIC, ""+order.toString()));

    }



}
