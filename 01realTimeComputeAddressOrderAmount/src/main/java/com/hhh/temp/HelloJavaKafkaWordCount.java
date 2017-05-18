package com.hhh.temp;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.hhh.temp.bean.Order;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.*;
import org.apache.spark.examples.streaming.StreamingExamples;
import org.apache.spark.streaming.Time;
import redis.clients.jedis.Jedis;
import scala.Tuple2;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

/**
 * Created by huanghh on 2017/5/16.
 */
@SuppressWarnings("ALL")
public class HelloJavaKafkaWordCount {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws InterruptedException {
//        if (args.length < 4) {
//            System.err.println("Usage: JavaKafkaWordCount <zkQuorum> <group> <topics> <numThreads>");
//            System.exit(1);
//        }
        String master = "";
        if (args.length > 0) {
            master = args[0];
        } else {
            master = "local[4]";
        }

        String zkQuorumP = "192.168.101.121:2181";
        String groupP = "test-consumer-group";
        String topicsP = "myTopic";
        String numThreadsP = "1";

        args = new String[]{
                zkQuorumP,
                groupP,
                topicsP,
                numThreadsP,
        };

        StreamingExamples.setStreamingLogLevels();
        SparkConf sparkConf = new SparkConf()
                .setMaster(master)
                .setAppName("HelloJavaKafkaWordCount");
        // Create the context with 2 seconds batch size
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(2000));

        int numThreads = Integer.parseInt(args[3]);
        Map<String, Integer> topicMap = new HashMap<>();
        String[] topics = args[2].split(",");
        for (String topic : topics) {
            topicMap.put(topic, numThreads);
        }

        JavaPairReceiverInputDStream<String, String> messages =
                KafkaUtils.createStream(jssc, args[0], args[1], topicMap);

        JavaDStream<Order> orders = messages.map(new Function<Tuple2<String, String>, Order>() {
            @Override
            public Order call(Tuple2<String, String> tuple2) throws Exception {
                String line = tuple2._2();
                System.out.println("messages.map-line:" + line);

                String json = "";
                if (line.startsWith("{")) {
                    json = line;
                }else{
                    throw new Exception("data format is not OK,Please check your data format is json");
                }
                System.out.println("messages.map-json:" + json);
                Gson gson = new Gson();
                Class<Order> clazz = Order.class;
                //转换为order
                Order order = gson.fromJson(json, clazz);

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//                //在转化为相应的词
//                String key = String.format("%s_%s",order.getAddress(),sdf.format(new Date(order.getDate())));
//                int value = order.getOrderAmount();
                return order;
            }
        });

//        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
//            @Override
//            public Iterator<String> call(String x) {
//                System.out.println("lines.flatMap_call:" + x);
//                return Arrays.asList(SPACE.split(x)).iterator();
//            }
//        });

        JavaPairDStream<String, Integer> words = orders
                .mapToPair(new PairFunction<Order, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(Order order) {
                        System.out.println("wordCounts:" + order);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        //                //在转化为相应的词
                        String key = String.format("%s_%s",order.getAddress(),sdf.format(new Date(order.getDate())));

                        return new Tuple2<>(key, order.getOrderAmount());
                    }
                });

        JavaPairDStream<String, Integer> wordCounts = words
                .reduceByKey(new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer i1, Integer i2) {
                        System.out.println(String.format("reduceByKey-i1:%d i2:%d", i1, i2));
                        int count = i1 + i2;

                        return count;
                    }
                });
        wordCounts.foreachRDD(new VoidFunction<JavaPairRDD<String, Integer>>() {
            @Override
            public void call(JavaPairRDD<String, Integer> stringIntegerJavaPairRDD) throws Exception {
                List<Tuple2<String, Integer>> output = stringIntegerJavaPairRDD.collect();
                String hashKey = "hash_address_amount_key";
                long len = jedis.hlen(hashKey);

                String key = "";
                int count = 0;
                for (Tuple2<String, Integer> tuple : output) {
                    key = tuple._1();
                    System.out.println("key:"+key);
                    count = tuple._2();
                    String value = "";
                    if (len>0) {
                        String redis_value = jedis.hget(hashKey,key);
                        value = String.format("%d",Integer.valueOf(redis_value) + count);
                    }else{
                        value = String.valueOf(count);
                    }
                    jedis.hset(hashKey,key,value);
                }
            }
        });

        wordCounts.print();
        jssc.start();
        jssc.awaitTermination();
    }

    private static Jedis jedis = new Jedis("192.168.101.121");
}
