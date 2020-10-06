package com.mygdx.game.models.data;

public class Word {
    private String word;
    private String translatedWord;
    public boolean allocated; // pig as the word?
    public boolean found; // word already founded?

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
