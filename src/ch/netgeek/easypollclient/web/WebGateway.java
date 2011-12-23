package ch.netgeek.easypollclient.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRequestDirector;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;
import ch.netgeek.easypollclient.polls.Poll;
import ch.netgeek.easypollclient.settings.Setting;
import ch.netgeek.easypollclient.settings.SettingsManager;

public class WebGateway {
    
    private String serverUrl;
    private String username;
    private String password;
    private DefaultHttpClient httpclient;
    private CookieStore cookieStore;
    private HttpContext localContext;
    
    public WebGateway(Context context) {
        SettingsManager settingsManager = new SettingsManager(context);
        Setting setting = settingsManager.readSettings();
        serverUrl = setting.getServerUrl();
        username = setting.getUsername();
        password = setting.getPassword();
        
        httpclient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        
        if (login() == false) {
            Log.d("demo", "Login FAILED");
        }
        
        ArrayList<Poll> polls = getPolls();
    }
    
    private boolean login() {

        try {
            
            HttpPost httppost = new HttpPost(serverUrl + "users/sign_in.xml");
            
            // Adding the user data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user[email]", username));
            nameValuePairs.add(new BasicNameValuePair("user[password]", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP post request
            HttpResponse response = httpclient.execute(httppost, localContext);
            String xmlResponse = EntityUtils.toString(response.getEntity());
            
            if (xmlResponse.contains("Invalid email or password")) {
                return false;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return false;
            
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
        
    }
    
    public ArrayList<Poll> getPolls() {
        
        if (login() == false) {
            return null;
        }
        
        try {
            
            HttpGet httpget = new HttpGet(serverUrl + "polls.xml");
            HttpResponse response = httpclient.execute(httpget, localContext);
            Log.d("demo", EntityUtils.toString(response.getEntity()));
        
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
