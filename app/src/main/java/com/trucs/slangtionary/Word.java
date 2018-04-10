package com.trucs.slangtionary;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Cody on 09/04/2018.
 */
@Entity(tableName = "word")
public class Word {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String word;

    public String description;

    public String language;

    public Word(String word, String description, String language) {
        this.word = word;
        this.description = description;
        this.language = language;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
