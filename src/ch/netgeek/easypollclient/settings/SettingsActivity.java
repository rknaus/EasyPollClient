package ch.netgeek.easypollclient.settings;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ch.netgeek.easypollclient.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {
    
    private String fileName;
    private Setting setting;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        setFileName(getFilesDir() + "/" + "settings.dat");
        if (!(new File(getFileName()).exists())) {
            writeSettings(this, new Setting());
        }
        setting = readSettings(this);
        
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
    
    /**
     * Saving settings
     * 
     * @param context
     * @param data
     */
    private void writeSettings(Context context, Setting setting) {
        
        FileOutputStream fos = null;
        ObjectOutputStream oos= null;
        
        try {
            File file = new File(getFileName());
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(setting);
            oos.flush();
            Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show();
        
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Settings not saved", Toast.LENGTH_SHORT).show();
            
        } finally {
            try {
                oos.close();
                fos.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Reading settings
     * 
     * @param context
     * @return
     */
    private Setting readSettings(Context context) {
        
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Setting setting = null;
        
        try {
            File file = new File(getFileName());
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            setting = (Setting) ois.readObject();
            Toast.makeText(context, "Settings read", Toast.LENGTH_SHORT).show();
            
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Settings not read", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                ois.close();
                fis.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return setting;
        
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
        
        if (username == null || username.equals("") || password == null || password.equals("")) {
            Toast.makeText(this, "Username and Password must be set!", Toast.LENGTH_SHORT).show();
        } else {
            writeSettings(this, new Setting(serverUrl, username, password));
            startActivity(new Intent(this, EasyPollClientActivity.class));
        }
        
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
