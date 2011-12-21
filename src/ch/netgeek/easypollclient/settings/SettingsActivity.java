package ch.netgeek.easypollclient.settings;

import ch.netgeek.easypollclient.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    
    private SettingsManager settingsManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        settingsManager = new SettingsManager(this);
        
        Setting setting = settingsManager.readSettings();
        
        // Showing the values in the Textfields
        String serverUrl = setting.getServerUrl();
        String username = setting.getUsername();
        String password = setting.getPassword();
        
        if (serverUrl != null) {
            EditText urlField = (EditText) this.findViewById(R.id.edit_text_settings_url);
            urlField.setText(serverUrl);
        }
        
        if (username != null) {
            EditText usernameField = (EditText) this.findViewById(R.id.edit_text_settings_username);
            usernameField.setText(username);
        }
        
        if (password != null) {
            EditText passwordField = (EditText) this.findViewById(R.id.edit_text_settings_password);
            passwordField.setText(password);
        }
    }

    public void cancel(View v) {
        startActivity(new Intent(this, EasyPollClientActivity.class));
    }
    
    public void save(View v) {
        
        EditText urlField = (EditText) this.findViewById(R.id.edit_text_settings_url);
        EditText usernameField = (EditText) this.findViewById(R.id.edit_text_settings_username);
        EditText passwordField = (EditText) this.findViewById(R.id.edit_text_settings_password);
        
        String serverUrl = urlField.getText().toString();
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        
        Setting setting = new Setting(serverUrl, username, password);
        
        if (setting.isValid()) {
            settingsManager.writeSettings(setting);
            startActivity(new Intent(this, EasyPollClientActivity.class));
        } else {
            Toast.makeText(this, "Username and Password must be set!", Toast.LENGTH_SHORT).show();
        }
        
    }
}
