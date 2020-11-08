package com.mygdx.game.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.models.Word;

import java.util.ArrayList;

public class Vocabulary {
    String vocName;
    ArrayList<Word> words;

    public Vocabulary(String vocName){
        this.vocName = vocName;
        words = new ArrayList<Word>();
    }

    public ArrayList<Word> getWords() {
        Gdx.app.log("PIGGY", String.valueOf(words.size()));
        return words;
    }

    public Word findWord(Word wordToFind)
    {
        for (Word word : words)
            if (word == wordToFind)
                return word;
        return null;
    }

    public void  addWord(Word word){
        words.add(word);
    }
    public  Word pickRandomWord(){
        return  words.get(MathUtils.random(0,words.size()-1));
    }
    public Word pickUnusedRandomWord(){
        Word word;
        do {
            word = words.get(MathUtils.random(0,words.size()-1));
        } while (word.allocated);
        return word;
    }
    public int countUsedWords(){
        int i = 0;
        for (Word word : words) {
            if(word.allocated == false)
                i++;
        }
        return i;
    }

    public void unallocateWord() {
        for(Word w: words){
            w.allocated = false;
        }
    }
}
