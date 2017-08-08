package com.afterqcd.study.java.vs;

/**
 * Created by afterqcd on 2017/5/4.
 */
public class KeyValueDemo {
    public static void main(String[] args) {
        KeyValue<String, Integer> keyValue = new KeyValue<>("ftp.port", 21);
        String key = keyValue.getKey();
        Integer value = keyValue.getValue();
    }
}

class KeyValue<K, V> {
    private K key;

    private V value;

    public KeyValue() {
    }

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
