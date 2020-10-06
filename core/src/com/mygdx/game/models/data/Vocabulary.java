package com.mygdx.game.models.data;

import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.AngryBird;

import java.util.ArrayList;

public class Vocabulary {
    String vocName;
    ArrayList<Word> words;

    public Vocabulary(String vocName){
        this.vocName = vocName;
        words = new ArrayList<Word>();
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public  void  addWord(Word word){
        words.add(word);
    }
    public  Word pickRandomWord(){
        return  words.get(MathUtils.random(0,words.size()-1));
    }
}
