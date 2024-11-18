package com.dimus.aek;

import android.graphics.Bitmap;

public class ResultM {
    private String mHomeTeam;
    private String mAwayTeam;
    private String mScoreHome;
    private String mScoreAway;
    private String mDate;
    private String mOrg;
    private Bitmap mHomeBitmap;
    private Bitmap mAwayBitmap;

    public ResultM(String date, String org, String mHomeTeam, String mAwayTeam, String mScoreHome, String mScoreAway,
                   Bitmap homeBitmap, Bitmap awayBitmap) {
        this.mHomeTeam = mHomeTeam;
        this.mAwayTeam = mAwayTeam;
        this.mScoreHome = mScoreHome;
        this.mScoreAway = mScoreAway;
        this.mDate = date;
        this.mOrg = org;
        this.mHomeBitmap = homeBitmap;
        this.mAwayBitmap = awayBitmap;
    }


    public String getHomeTeam() {
        return mHomeTeam;
    }
    public void setHomeTeam(String mHomeTeam) {
        this.mHomeTeam = mHomeTeam;
    }

    public String getAwayTeam() {
        return mAwayTeam;
    }
    public void setAwayTeam(String mAwayTeam) {
        this.mAwayTeam = mAwayTeam;
    }

    public String getScoreHome() {
        return mScoreHome;
    }
    public void setScoreHome(String mScoreHome) {
        this.mScoreHome = mScoreHome;
    }

    public String getScoreAway() {
        return mScoreAway;
    }
    public void setScoreAway(String mScoreAway) {
        this.mScoreAway = mScoreAway;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getOrg() {
        return mOrg;
    }

    public void setOrg(String mOrg) {
        this.mOrg = mOrg;
    }

    public Bitmap getHomeBitmap() {return mHomeBitmap;}
    public void setHomeBitmap(Bitmap mHomeBitmap) {this.mHomeBitmap = mHomeBitmap;}

    public Bitmap getAwayBitmap() {return mAwayBitmap;}
    public void setAwayBitmap(Bitmap mAwayBitmap) {this.mAwayBitmap = mAwayBitmap;}
}
