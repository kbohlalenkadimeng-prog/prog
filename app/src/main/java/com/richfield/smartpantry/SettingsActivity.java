package com.richfield.smartpantry;

import android.os.Bundle;import android.content.SharedPreferences;import android.widget.Switch;import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity{
    private static final String PREFS="smart_pantry_settings";
    @Override protected void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_settings);Switch sw=findViewById(R.id.switchExpiry);SharedPreferences p=getSharedPreferences(PREFS,MODE_PRIVATE);sw.setChecked(p.getBoolean("expiryAlerts",true));sw.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("expiryAlerts",checked).apply());findViewById(R.id.btnSettingsBack).setOnClickListener(v->finish());}
}
