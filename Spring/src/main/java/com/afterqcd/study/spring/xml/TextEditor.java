package com.afterqcd.study.spring.xml;

/**
 * Created by afterqcd on 16/6/7.
 */
public class TextEditor {
    private SpellChecker spellChecker;

    public TextEditor() {

    }

    public TextEditor(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public void setSpellChecker(SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public void spellCheck() {
        spellChecker.check();
    }
}
