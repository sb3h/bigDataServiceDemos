package com.hhh.temp.hive;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public final class Tuomin extends UDF {
    public Text evaluate(final Text s) {
        if (s == null) {
            return null;
        }
        String str = s.toString();
        String afterTuomin = getTuomin(str);
        System.out.println(afterTuomin);

        return new Text(s.toString().toLowerCase());
    }

    public static void main(String[] args) {
        System.out.println(getTuomin("xiaoming"));
    }

    private static String getTuomin(String str) {
        String afterTuomin = "";
        char first = str.charAt(0);
        char last = str.charAt(str.length()-1);

        StringBuffer sb = new StringBuffer();
        sb.append(first);
        for (int i = 0; i < str.length()-2; i++) {
            sb.append("*");
        }
        sb.append(last);
        afterTuomin = sb.toString();
        return afterTuomin;
    }
}