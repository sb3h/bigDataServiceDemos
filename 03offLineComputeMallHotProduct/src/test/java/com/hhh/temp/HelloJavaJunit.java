package com.hhh.temp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by huanghh on 2017/5/16.
 */
public class HelloJavaJunit {
    @Test
    public void testJava() {
        System.out.println("com.hhh.temp.HelloJavaJunit");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Iterator<Integer> ii = list.iterator();
        while (ii.hasNext()){
            System.out.println(ii.next());
        }
//        for(int i :is){
//            System.out.println(i);
//        }
    }
}
