package com.afterqcd.study.scala.javausecaseclass;

/**
 * Created by afterqcd on 16/8/17.
 */
public class JavaUseCaseClassDemo {
    public static void main(String[] args) {
        People wangWu = new People("wangWu");
        System.out.println(wangWu);
        People zhangSan = People$.MODULE$.apply("zhangsan");
        System.out.println(zhangSan.name());
    }
}
