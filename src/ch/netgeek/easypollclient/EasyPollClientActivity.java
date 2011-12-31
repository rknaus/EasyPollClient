package ch.netgeek.easypollclient;

import ch.netgeek.easypollclient.polls.AnsweredPollsActivity;
import ch.netgeek.easypollclient.polls.UnansweredPollsActivity;
import ch.netgeek.easypollclient.settings.Setting;
import ch.netgeek.easypollclient.settings.SettingsActivity;
import ch.netgeek.easypollclient.settings.SettingsManager;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

public class EasyPollClientActivity extends TabActivity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        intent = new Intent().setClass(this, UnansweredPollsActivity.class);
        spec = tabHost.newTabSpec("unanswered").setIndicator("Unanswered", 
                res.getDrawable(R.drawable.ic_tab_unanswered)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, AnsweredPollsActivity.class);
        spec = tabHost.newTabSpec("answered").setIndicator("Answered", 
                res.getDrawable(R.drawable.ic_tab_answered)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, SettingsActivity.class);
        spec = tabHost.newTabSpec("settings").setIndicator("Settings", 
                res.getDrawable(R.drawable.ic_tab_unanswered)).setContent(intent);
        tabHost.addTab(spec);
        
        SettingsManager settingsManager = new SettingsManager(this);
        Setting setting = settingsManager.readSettings();
        if (setting != null && setting.isValid()) {
            tabHost.setCurrentTab(0);
        } else {
            Toast.makeText(this, "Please complete the settings", Toast.LENGTH_LONG).show();
            tabHost.setCurrentTab(2);
        }
        
    }
    
    public void polls(View v) {
        
        SettingsManager settingsManager = new SettingsManager(this);
        Setting setting = settingsManager.readSettings();
        if (setting != null && setting.isValid()) {
            startActivity(new Intent(this, UnansweredPollsActivity.class));
        } else {
            Toast.makeText(this, "Please complete the settings", Toast.LENGTH_LONG).show();
        }
    }
    
    public void settings(View v) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}