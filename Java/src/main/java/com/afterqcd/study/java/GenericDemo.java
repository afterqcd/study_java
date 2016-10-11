package com.afterqcd.study.java;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by afterqcd on 16/9/21.
 */
public class GenericDemo {
    public static void main(String[] args) {
        List<Long> longs = Lists.newArrayList(1L);
        System.out.println(longs.getClass());
    }
}


