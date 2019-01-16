package com.heally.healthtrackerapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * An exercise diary. Instances represent a single diary entry.
 */
@Entity
public class Diary {
// some help here: https://guides.codepath.com/android/Room-Guide

    /**
     * Unique id.
     */
    @PrimaryKey(autoGenerate=true)
    private long id;

    /**
     * The title of this exercise diary entry. For example, "Finger Exercise".
     */
    @ColumnInfo
    private String title;

    /**
     * The repetitions performed of the exercise.
     */
    @ColumnInfo
    private int quantity;

    /**
     * A description of the exercise performed, or how it went.
     */
    @ColumnInfo
    private String description;

    /**
     * The timestamp when the exercise was performed.
     */
    @ColumnInfo
    private String timestamp;

    public String getTitle() {
        return title;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
