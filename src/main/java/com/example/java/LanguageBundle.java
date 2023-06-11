package com.example.java;

public class LanguageBundle {

    public static LanguageBundle INSTANCE;

    private String xxx;

    public String getXxx() {
        return xxx;
    }

    public LanguageBundle setXxx(String xxx) {
        this.xxx = xxx;
        return this;
    }
}
