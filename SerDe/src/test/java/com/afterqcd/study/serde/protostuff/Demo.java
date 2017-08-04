package com.afterqcd.study.serde.protostuff;

import io.protostuff.runtime.RuntimeSchema;

import java.util.List;

/**
 * Created by afterqcd on 2017/4/14.
 */
public class Demo {
    @org.junit.Test
    public void test() throws Exception {
        RuntimeSchema<Test> schema = RuntimeSchema.createFrom(Test.class);
    }
}

class Test {
    private String s;
    private int i;
    private List<String> list;

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
