package com.samplecodeapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chapter {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("chapter")
    @Expose
    private String chapter;
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("hindi")
    @Expose
    private List<HindiPojo> hindi = null;

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

    public List<HindiPojo> getHindi() {
        return hindi;
    }

    public void setHindi(List<HindiPojo> hindi) {
        this.hindi = hindi;
    }

    @Override
    public String toString() {
        return "{" +
                "\"title\": \"" + title + "\"" +
                ", \"description\": \"" + description + "\"" +
                ", \"chapter\": \"" + chapter + "\"" +
                ", \"heading\": \"" + heading + "\"" +
                ", \"hindi\": " + hindi +
                '}';
    }
}
