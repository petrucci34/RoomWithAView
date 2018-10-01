package com.korhan34.roomwithaview;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class WordDao {
    @Insert
    abstract void insert(Word word);

    @Query("DELETE FROM word_table")
    abstract void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC")
    abstract LiveData<List<Word>> getAllWords();
}
