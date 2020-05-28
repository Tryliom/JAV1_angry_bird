package com.mygdx.game.models;


public class Score {
    protected int score;

    public Score()
    {
        score = 0;
    }

    public void calculate(PhysicalObject object)
    {
        if(object.getClass() == Pig.class)
            add(((Pig)object).getPoints());
        else if(object.getClass() == Tnt.class)
            remove(((Tnt)object).getNegativePoints());
    }

    protected void add(int points)
    {
        score += points;
    }

    protected void remove(int points)
    {
        score -= points;
        if(score < 0) score = 0;
    }

    public int getScore() {
        return score;
    }
}
