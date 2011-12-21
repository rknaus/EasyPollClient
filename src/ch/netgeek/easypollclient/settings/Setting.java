package ch.netgeek.easypollclient.settings;

import java.io.Serializable;

public class Setting implements Serializable {

    // Variable declaration
    private static final long serialVersionUID = 6189768950215131481L;
    private String serverUrl;
    private String username;
    private String password;
    
    public Setting () {
        setServerUrl("http://10.211.55.2:3000/");
    }
    
    public Setting (String serverUrl, String username, String password) {
        setServerUrl(serverUrl);
        setUsername(username);
        setPassword(password);
    }
    
    public String getServerUrl() {
        return serverUrl;
    }
    
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isValid() {
        boolean valid = true;
        if (getServerUrl() == null || getServerUrl().equals("")) {
            valid = false;
        }
        if (getUsername() == null || getUsername().equals("")) {
            valid = false;
        }
        if (getPassword() == null || getPassword().equals("")) {
            valid = false;
        }
        return valid;
    }

    @Override
    public String toString() {
        return "Setting [serverUrl=" + serverUrl + ", username=" + username
                + ", password=" + password + "]";
    }
}
