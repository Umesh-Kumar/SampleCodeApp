package com.samplecodeapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HindiPojo {

    @SerializedName("a_n")
    @Expose
    private Integer aN;
    @SerializedName("s_n")
    @Expose
    private Integer sN;
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("t")
    @Expose
    private String t;
    @SerializedName("d")
    @Expose
    private String d;
    @SerializedName("sub_heading")
    @Expose
    private String subHeading;
    @SerializedName("likes")
    @Expose
    private int likes;

    public Integer getAN() {
        return aN;
    }

    public void setAN(Integer aN) {
        this.aN = aN;
    }

    public Integer getSN() {
        return sN;
    }

    public void setSN(Integer sN) {
        this.sN = sN;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "{" +
                "\"aN\":" + aN +
                ", \"sN\":" + sN +
                ", \"s\":\"" + s + "\"" +
                ", \"t\":\"" + t + "\"" +
                ", \"d\":\"" + d + "\"" +
                ", \"subHeading\":\"" + subHeading + "\"" +
                ", \"likes\":" + likes +
                '}';
    }
}
