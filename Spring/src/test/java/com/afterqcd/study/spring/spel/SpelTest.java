package com.afterqcd.study.spring.spel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.junit.Assert.*;

import java.util.Properties;

/**
 * Created by afterqcd on 2016/12/20.
 */
public class SpelTest {
    private ExpressionParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new SpelExpressionParser();
    }

    @After
    public void tearDown() throws Exception {
        parser = null;
    }

    @Test
    public void shouldParseLiteral() throws Exception {
        Expression expression = parser.parseExpression("'Hello World'");
        assertEquals("Hello World", expression.getValue());
    }

    @Test
    public void shouldInvokeMethodOnLiteral() throws Exception {
        Expression expression = parser.parseExpression("'Hello World'.length()");
        assertEquals("Hello World".length(), expression.getValue());
    }

    @Test
    public void shouldConcatString() throws Exception {
        Expression expression = parser.parseExpression("'Hello' + ' ' + 'World'");
        assertEquals("Hello World", expression.getValue());
    }

    @Test
    public void shouldAccessObjectProperties() throws Exception {
        User john = new User(15, "John");
        assertEquals(15, parser.parseExpression("age").getValue(john));
        assertEquals("John", parser.parseExpression("name").getValue(john));
    }

    @Test
    public void shouldAccessObjectMethod() throws Exception {
        User john = new User(15, "John");
        assertEquals(true, parser.parseExpression("isTeenager()").getValue(john));
    }
}

class User {
    private int age;
    private String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTeenager() {
        return age >= 10 && age < 20;
    }
}
