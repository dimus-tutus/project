package com.dimus.aek;

import android.graphics.Bitmap;

public class Table {
    private String mPosition;
    private String mTeam;
    private String mMatchesPlayed;
    private String mWins;
    private String mDraws;
    private String mLoses;
    private String mGoals;
    private Bitmap mTeamBitmap;
    private String mPoints;
    private boolean mExpanded;

    public Table(String position, Bitmap teamBitmap, String team, String matchesPlayed, String points, String wins, String draws, String loses, String goals){
        this.mPosition = position;
        this.mTeam = team;
        this.mMatchesPlayed = matchesPlayed;
        this.mWins = wins;
        this.mDraws = draws;
        this.mLoses = loses;
        this.mGoals = goals;
        this.mTeamBitmap = teamBitmap;
        this.mPoints = points;
        this.mExpanded = false;
    }
    public boolean isExpanded(){return mExpanded;}
    public void setExpanded(boolean expanded){this.mExpanded = expanded;}

    public String getPosition() {return mPosition;}
    public void setPosition(String mPosition) {this.mPosition = mPosition;}

    public String getTeam() {return mTeam;}
    public void setTeam(String mTeam) {this.mTeam = mTeam;}

    public String getMatchesPlayed() {return mMatchesPlayed;}
    public void setMatchesPlayed(String mMatchesPlayed) {this.mMatchesPlayed = mMatchesPlayed;}

    public String getWins() {return mWins;}
    public void setWins(String mWins) {this.mWins = mWins;}

    public String getDraws() {return mDraws;}
    public void setDraws(String mDraws) {this.mDraws = mDraws;}

    public String getLoses() {return mLoses;}
    public void setLoses(String mLoses) {this.mLoses = mLoses;}

    public String getGoals() {return mGoals;}
    public void setGoals(String mGoals) {this.mGoals = mGoals;}

    public Bitmap getTeamBitmap() {return mTeamBitmap;}
    public void setTeamBitmap(Bitmap mTeamBitmap) {this.mTeamBitmap = mTeamBitmap;}

    public String getPoints() {return mPoints;}
    public void setPoints(String mPoints) {this.mPoints = mPoints;}
}
