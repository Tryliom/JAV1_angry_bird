package com.mygdx.game.exam;

import com.mygdx.game.customExceptions.TranslationDoesNotExistException;
import com.mygdx.game.customExceptions.TranslationExistsException;

import java.util.HashMap;

public class SemanticWord {
    private HashMap<String, String> list = new HashMap<>();

    public void addTranslation(String language, String value) throws TranslationExistsException {
        this.list.put(language, value);
    }

    public String getValue(String language) throws TranslationDoesNotExistException {
        return this.list.get(language);
    }
}
