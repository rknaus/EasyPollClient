package ch.netgeek.easypollclient.polls;

import ch.netgeek.easypollclient.R;
import ch.netgeek.easypollclient.web.WebGateway;
import android.app.Activity;
import android.os.Bundle;

public class PollsActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polls);
        
        
        new WebGateway(this);
    }
    
}
