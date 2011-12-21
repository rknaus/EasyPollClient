package ch.netgeek.easypollclient.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class SettingsManager {
    
    private String fileName;
    private Context context;
    
    public SettingsManager(Context context) {
        
        this.context = context;
        
        setFileName(context.getFilesDir() + "/" + "settings.dat");
        
        //TODO DELETE
        Log.d("demo", getFileName());
        
        if (!(new File(getFileName()).exists())) {
            writeSettings(new Setting());
        }
    }

    public void writeSettings(Setting setting) {

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

    public Setting readSettings() {

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Setting setting = null;

        try {
            File file = new File(getFileName());
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            setting = (Setting) ois.readObject();

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
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
