package com.heally.healthtrackerapp;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DiaryDao {

    @Query("SELECT * FROM diary")
    List<Diary> getAll();

    @Insert
    public Long insertDiary(Diary diary);

}
