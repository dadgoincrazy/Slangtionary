package com.trucs.slangtionary;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Cody on 09/04/2018.
 */
@Dao
public interface WordDao {
    @Query("SELECT * FROM word")
    LiveData<List<Word>> getAll();

    @Query("SELECT * FROM word WHERE id = :word_id LIMIT 1")
    LiveData<Word> get(Long word_id);

    @Query("SELECT * FROM word WHERE word LIKE :word")
    LiveData<List<Word>> getLikeWord(String word);

    @Insert
    Long insert(Word word);
}
