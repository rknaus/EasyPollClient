package ch.netgeek.easypollclient;

import ch.netgeek.easypollclient.settings.SettingsActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EasyPollClientActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void polls(View v) {
        Toast.makeText(EasyPollClientActivity.this, 
                "List Polls...", Toast.LENGTH_LONG).show();
    }
    
    public void settings(View v) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}