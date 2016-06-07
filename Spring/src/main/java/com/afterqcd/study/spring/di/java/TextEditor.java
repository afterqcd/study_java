package com.afterqcd.study.spring.di.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by afterqcd on 16/6/7.
 */
@Component
public class TextEditor {
    @Autowired
    private SpellChecker spellChecker;

    public void spellCheck() {
        spellChecker.check();
    }
}
