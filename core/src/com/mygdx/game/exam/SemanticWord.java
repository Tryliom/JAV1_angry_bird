package com.mygdx.game.exam;

import java.util.HashMap;

public class SemanticWord {
    private HashMap<String, String> list;

    public void addTranslation(String language, String value) {
        this.list.put(language, value);
    }

    public String getValue(String language) {
        return this.list.get(language);
    }
}
