package com.mygdx.game.exam;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Vocabulary {
    private String vocName;
    private ArrayList<SemanticWord> list;

    public Vocabulary(String vocName) {
        this.vocName = vocName;
        this.list = new ArrayList<SemanticWord>();
    }

    public void addSemanticWord(SemanticWord word) {
        this.list.add(word);
    }

    public SemanticWord pickAWord() {
        int randomWord = (int) Math.floor(Math.random() * this.list.size());
        return this.list.get(randomWord);
    }


    public String getVocName() {
        return vocName;
    }
}
