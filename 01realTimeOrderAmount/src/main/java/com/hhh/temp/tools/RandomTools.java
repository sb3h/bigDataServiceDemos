package com.hhh.temp.tools;

import java.util.Random;

/**
 * Created by huanghh on 2017/5/16.
 */
public class RandomTools {
    public static int getRandomI(int maxI) {
        int randomI = getRandomI(0,maxI);
        return randomI;
    }
    public static int getRandomI(int minI,int maxI) {
        if (minI > maxI) {
            maxI = minI;
            minI = 0;
        }

        Random random = new Random();

        int randomI = random.nextInt(maxI);
        if (randomI < minI) {
            randomI = randomI + minI;
        }
        return randomI;
    }
}
