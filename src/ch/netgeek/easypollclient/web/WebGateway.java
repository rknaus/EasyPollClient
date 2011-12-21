package ch.netgeek.easypollclient.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
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
    
    public WebGateway(Context context) {
        SettingsManager settingsManager = new SettingsManager(context);
        Setting setting = settingsManager.readSettings();
        serverUrl = setting.getServerUrl();
        username = setting.getUsername();
        password = setting.getPassword();
        
        httpclient = new DefaultHttpClient();
        cookieStore = new BasicCookieStore();
        httpclient.setCookieStore(cookieStore);
        
        login();
        
    }
    
    private void login() {
        
        HttpPost httppost = new HttpPost(serverUrl + "users/sign_in.xml");
        
        Log.d("demo", httppost.getURI().toString());
        
        HttpResponse response = null;
        
        try {
            
            // Adding the user data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user[email]", username));
            nameValuePairs.add(new BasicNameValuePair("user[password]", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            // Preparing HTTP response
            CookieStore cookieStore = new BasicCookieStore();
            HttpContext localContext = new BasicHttpContext();
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            
            // Execute HTTP post request
            response = httpclient.execute(httppost, localContext);
            
            Cookie sessionCookie = null;
            
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = cookies.size() - 1; i >= 0; i--) {
                Cookie c = cookies.get(i);
                Log.d("demo", "comment: " + c.getComment() + ", commentURL: " + c.getCommentURL() + ", domain: " + c.getDomain().toString() + ", expires: " + c.getExpiryDate() + ", name: " + c.getName() + ", path: " + c.getPath() + ", value: " + c.getValue() + ", version: " + c.getVersion());
                
                if (c.getName().equals("_session_id")) {
                    sessionCookie = c;
                }
                
            }
            
            HttpGet httpget = new HttpGet(serverUrl + "polls.xml");
            response = httpclient.execute(httpget, localContext);
            Log.d("demo", EntityUtils.toString(response.getEntity()));
            
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (response != null) {
            
            Log.d("demo", "RESPONSE PARAMETER:" + response.getParams().toString());
            
            Header[] header = response.getAllHeaders();
            for (int i = 0; i < header.length; i++) {
                Log.d("demo", "Header Name " + i + ": " + header[i].getName());
                Log.d("demo", "Header Value " + i + ": " + header[i].getValue());
            }
            Log.d("demo", response.toString());
            
        }
        
    }
    
    public ArrayList<Poll> getPolls() {
        return null;
    }
    
}
