package com.mygdx.game.models.data;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class VocabularyProvider {
    static  VocabularyProvider singleInstance = null;
    public ArrayList<Vocabulary> vocabularies;

    public VocabularyProvider getInstance(){
        if(singleInstance == null)
            singleInstance = new VocabularyProvider();
        return singleInstance;
    }
    private  VocabularyProvider(){}
    public Vocabulary pickRandomVocabulary(){
        return  vocabularies.get(MathUtils.random(0,vocabularies.size()-1));
    }
}
