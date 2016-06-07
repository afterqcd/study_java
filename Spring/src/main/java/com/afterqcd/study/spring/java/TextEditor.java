package com.afterqcd.study.spring.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
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
