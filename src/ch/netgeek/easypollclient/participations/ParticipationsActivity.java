package ch.netgeek.easypollclient.participations;

import java.util.ArrayList;

import ch.netgeek.easypollclient.EasyPollClientActivity;
import ch.netgeek.easypollclient.R;
import ch.netgeek.easypollclient.web.WebGateway;
import ch.netgeek.easypollclient.polls.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ParticipationsActivity extends Activity {
    
    private WebGateway webGateway;
    private Participation participation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participations);
        
        int pollId = getIntent().getIntExtra("poll_id", 0);
        
        webGateway = new WebGateway(this);
        Poll poll = webGateway.getPoll(pollId);
        
        if (poll == null) {
            Log.d("demo", "POLL IS NULL!!");
        } else {
            Log.d("demo", "POLL IS NOT NULL!!");
            Log.d("demo", "Id: " + String.valueOf(poll.getPollId()));
            Log.d("demo", "Title: " + poll.getTitle());
            Log.d("demo", "Published at: " + poll.getPublishedAt().toString());
            Log.d("demo", "Category: " + poll.getCategory());
            Log.d("demo", "Username: " + poll.getUserName());
            Log.d("demo", "Questions Count: " + String.valueOf(poll.getQuestionsCount()));
            Log.d("demo", "Participations Count: " + String.valueOf(poll.getParticipationsCount()));
            
            ArrayList<Question> questions = poll.getQuestions();
            Log.d("demo", "QUESTIONS: " + questions.size());
            
            for (int i = 0; i < questions.size(); i++) {
                
            Question question = questions.get(i);
            
            Log.d("demo", "  Question Id: " + question.getQuestionId());
            Log.d("demo", "  Question Text: " + question.getText());
            Log.d("demo", "  Question Kind: " + question.getKind());
                
                ArrayList<Option> options = question.getOptions();
                Log.d("demo", "OPTIONS: " + options.size());
                
                for (int k = 0; k < options.size(); k++) {
                    Option option = options.get(k);
                    
                    Log.d("demo", "    Option Id: " + option.getOptionId());
                    Log.d("demo", "    Option Text: " + option.getText());
                }
                
            }
            
        }
        
    }
    
    private void registerButtonEvents() {
        Button backButton = (Button) findViewById(R.id.button_participations_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
            }
        });
    }
    
    public void back(View v) {
        
    }
    
    public void cancel(View v) {
        startActivity(new Intent(this, PollsActivity.class));
    }
    
    public void next(View v) {
        
    }
}
