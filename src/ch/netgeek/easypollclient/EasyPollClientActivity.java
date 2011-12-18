package ch.netgeek.easypollclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EasyPollClientActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button listPollsButton = (Button) findViewById(R.id.button_list_polls);
        listPollsButton.setOnClickListener(
                new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EasyPollClientActivity.this, 
                                "List Polls...", Toast.LENGTH_LONG).show();
                        
                    }
                });
        
        Button userSettingsButton = (Button) findViewById(R.id.button_user_settings);
        userSettingsButton.setOnClickListener(
                new View.OnClickListener() {
                    
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EasyPollClientActivity.this, 
                                "User Settings...", Toast.LENGTH_LONG).show();
                        
                    }
                });
    }
}