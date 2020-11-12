package com.mygdx.game.exam;

public class Language {
    private String displayName;
    private String ISO_639_1;

    public Language(String displayName, String ISO_639_1) {
        this.displayName = displayName;
        this.ISO_639_1 = ISO_639_1;
    }


    public String getDisplayName() {
        return displayName;
    }

    public String getISO_639_1() {
        return ISO_639_1;
    }
}
