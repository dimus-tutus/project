package com.dimus.aek;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

public class Match {

    private String mDateTime;
    private String mDate;
    private String mDay;
    private String mTime;
    private String mHomeTeam;
    private String mAwayTeam;
    private String mOrg;
    private String mChannel;
    private String mStadium;
    private boolean mExpanded;
    private Bitmap mHomeTeamBitmap;
    private Bitmap mAwayTeamBitmap;

    public Match(String dateTime, String org, String homeTeam, String awayTeam, String channel, Bitmap homeTeamBitmap, Bitmap awayTeamBitmap){
        mDateTime = dateTime;
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
        mOrg = org;
        mChannel = channel;
        mExpanded = false;
        mHomeTeamBitmap = homeTeamBitmap;
        mAwayTeamBitmap = awayTeamBitmap;


        if (mChannel == "-") {
            String[] mIsNovaList = new String[]{"ΠΑΟΚ", "Άρης", "Ατρόμητος", "Αστέρας Τρίπολης", "ΠΑΣ Γιάννινα", "Πανσερραϊκός"};

            for (String string : mIsNovaList) {
                if (mHomeTeam.contains(string)) {
                    mChannel = "NOVA";
                    break;
                } else mChannel = "COSMOTE";
            }
        }
    }

    public Match(String day, String date, String time, String homeTeam, String awayTeam, String stadium, String channel, Bitmap homeTeamBitmap, Bitmap awayTeamBitmap){
        mDay = day;
        mDate = date;
        mTime = time;
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
        mChannel = channel;
        mStadium = stadium;
        mExpanded = false;
        mHomeTeamBitmap = homeTeamBitmap;
        mAwayTeamBitmap = awayTeamBitmap;


        if (mChannel.isEmpty()) {
            String[] mIsNovaList = new String[]{"Π.Α.Ο.Κ.", "ΑΡΗΣ", "ΑΤΡΟ", "ΑΣΤ", "ΠΑΣ", "ΠΑΝΣ"};

            for (String string : mIsNovaList) {
                if (mHomeTeam.contains(string)) {
                    mChannel = "NOVA";
                    break;
                } else mChannel = "COSMOTE";
            }
        }
    }
    public boolean isExpanded(){return mExpanded;}
    public void setExpanded(boolean expanded){this.mExpanded = expanded;}


    public String getDateTime() {return mDateTime;}
    public void setDateTime(String Date) {this.mDateTime = Date;}

    public String getHomeTeam() {return mHomeTeam;}
    public void setHomeTeam(String HomeTeam) {this.mHomeTeam = HomeTeam;}

    public String getAwayTeam() {return mAwayTeam;}
    public void setAwayTeam(String AwayTeam) {this.mAwayTeam = AwayTeam;}

    public String getOrg() {return mOrg;}
    public void setOrg(String Org) {this.mOrg = Org;}

    public String getChannel() {return mChannel;}
    public void setChannel(String mChannel) {this.mChannel = mChannel;}

    public Bitmap getHomeTeamBitmap() {
        return mHomeTeamBitmap;
    }
    public void setHomeTeamBitmap(Bitmap mHomeTeamBitmap) {this.mHomeTeamBitmap = mHomeTeamBitmap;}

    public Bitmap getAwayTeamBitmap() {
        return mAwayTeamBitmap;
    }
    public void setAwayTeamBitmap(Bitmap mAwayTeamBitmap) {this.mAwayTeamBitmap = mAwayTeamBitmap;}

    public String getDate() {return mDate;}
    public void setDate(String mDate) {this.mDate = mDate;}

    public String getTime() {return mTime;}
    public void setTime(String mTime) {this.mTime = mTime;}

    public String getStadium() {return mStadium;}
    public void setStadium(String mStadium) {this.mStadium = mStadium;}

    public String getDay() {return mDay;}
    public void setDay(String mDay) {this.mDay = mDay;}
}
