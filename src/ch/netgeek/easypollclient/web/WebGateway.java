package ch.netgeek.easypollclient.web;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import ch.netgeek.easypollclient.polls.Poll;
import ch.netgeek.easypollclient.settings.Setting;
import ch.netgeek.easypollclient.settings.SettingsManager;

public class WebGateway {
    
    private Context context;
    private String serverUrl;
    private String username;
    private String password;
    private DefaultHttpClient httpclient;
    private CookieStore cookieStore;
    private HttpContext localContext;
    
    public WebGateway(Context context) {
        this.context = context;
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
        
        for (int i = 0; i < polls.size(); i++) {
            Log.d("demo", polls.get(i).toString());
        }
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
        
        ArrayList<Poll> polls = new ArrayList<Poll>();
        
        if (login() == false) {
            Toast.makeText(context, "Login Failure!", Toast.LENGTH_LONG).show();
            return null;
        }
        
        // Getting the polls list via HTTP
        String xmlResponse;
        
        try {
            HttpGet httpget = new HttpGet(serverUrl + "polls.xml");
            HttpResponse response = httpclient.execute(httpget, localContext);
            xmlResponse = EntityUtils.toString(response.getEntity());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        // Parsing the XML response
        Element root = null;
        
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new StringReader(xmlResponse));
            root = doc.getRootElement();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // iterating over the xml <poll> elements
        String pollTag = "poll";
        String pollIdTag = "id";
        String pollTitleTag = "title";
        String pollPublishedAtTag = "published_at";
        String pollCategoryTag = "category";
        String pollUserNameTag = "user_name";
        String pollQuestionsCountTag = "questions_count";
        String pollParticipationsCountTag = "participations_count";
        
        Iterator<?> lineIterator = root.getChildren(pollTag).iterator();
        while (lineIterator.hasNext()) {
            Element pollElement = (Element) lineIterator.next();
            
            /* 
             * getting the id, title, published_at, category, user_name, 
             * questions_count and participations_count Elements
             */
            int pollId = Integer.valueOf(pollElement.getChildText(pollIdTag));
            String title = pollElement.getChildText(pollTitleTag);
            Date publishedAt = Date.valueOf(pollElement.getChildText(pollPublishedAtTag));
            String category = pollElement.getChildText(pollCategoryTag);
            String userName = pollElement.getChildText(pollUserNameTag);
            int questionsCount = Integer.valueOf(pollElement.getChildText(pollQuestionsCountTag));
            int participationsCount = Integer.valueOf(pollElement.getChildText(pollParticipationsCountTag));
            
            polls.add(new Poll(pollId, title, publishedAt, category, userName, 
                    questionsCount, participationsCount));
        }
        
        if (polls.isEmpty()) {
            return null;
        }
        
        return polls;
    }
    
}
