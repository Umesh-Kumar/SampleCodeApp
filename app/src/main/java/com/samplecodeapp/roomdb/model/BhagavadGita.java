package com.samplecodeapp.roomdb.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bhagavad_gita_table")
public class BhagavadGita {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int priority;
    private String title;
    private String description;
    private String chapter;
    private String heading;
    private String hindiJson;
    private int likes;

    public BhagavadGita(String title, String description, String chapter, String heading, String hindiJson, int priority, int likes) {
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.chapter = chapter;
        this.heading = heading;
        this.hindiJson = hindiJson;
        this.likes = likes;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getHindiJson() {
        return hindiJson;
    }

    public void setHindiJson(String hindiJson) {
        this.hindiJson = hindiJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
