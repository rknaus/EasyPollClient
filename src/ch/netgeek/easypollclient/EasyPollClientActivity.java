package ch.netgeek.easypollclient;

import ch.netgeek.easypollclient.settings.Setting;
import ch.netgeek.easypollclient.settings.SettingsActivity;
import ch.netgeek.easypollclient.settings.SettingsManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class EasyPollClientActivity extends Activity {
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void polls(View v) {
        
        SettingsManager settingsManager = new SettingsManager(this);
        Setting setting = settingsManager.readSettings();
        if (setting != null && setting.isValid()) {
            getPollsList();
        }
    }
    
    private void getPollsList() {
        Log.d("demo", "GETTING POLLS LIST");
    }
    
    public void settings(View v) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}