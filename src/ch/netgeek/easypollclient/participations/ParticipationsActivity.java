package ch.netgeek.easypollclient.participations;

import ch.netgeek.easypollclient.R;
import ch.netgeek.easypollclient.web.WebGateway;
import android.app.Activity;
import android.os.Bundle;

public class ParticipationsActivity extends Activity {
    
    private WebGateway webGateway;
    private Participation participation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participations);
        
        webGateway = new WebGateway(this);
        
    }
}
