package com.afterqcd.study.java.vs;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by afterqcd on 2017/5/4.
 */
public class SortDemo {
    public static void main(String[] args) {
        List<Person> persons = Lists.newArrayList();
        persons.stream()
                .filter(p -> p.getAge() != null && p.getName() != null)
                .sorted((p1, p2) -> {
                    int ageOrder = p1.getAge().compareTo(p2.getAge());
                    if (ageOrder != 0) {
                        return ageOrder;
                    }
                    return p1.getName().compareTo(p2.getName());
                })
                .collect(Collectors.toList());
    }
}
