package com.mygdx.game.models.data;

public class Word {
    private String word;
    private String translatedWord;
    private boolean allocated; // pig as the word?
    private boolean found; // word already founded?

    public Word(String word, String translatedWord){
        this.word = word;
        this.translatedWord = translatedWord;
        allocated = false;
        found = false;
    }

    public String getWord(){
        return  word;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }
}
