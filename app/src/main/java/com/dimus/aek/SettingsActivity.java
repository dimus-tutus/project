package com.dimus.aek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mPrefs = getSharedPreferences("settings", MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }

    @Override
    protected void onPause(){
        super.onPause();

        mEditor.commit();
    }
}