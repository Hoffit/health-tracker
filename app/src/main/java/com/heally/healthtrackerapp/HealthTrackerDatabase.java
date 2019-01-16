package com.heally.healthtrackerapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Diary.class}, version = 1)
public abstract class HealthTrackerDatabase extends RoomDatabase {

    public abstract DiaryDao diaryDao();

    protected final static String NAME = "HealthTrackerDatabase";
}
