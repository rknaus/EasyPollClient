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
import android.widget.TextView;
import android.widget.Toast;

public class ParticipationsActivity extends Activity {
    
    private WebGateway webGateway;
    private Poll poll;
    private ArrayList<Question> questions;
    private Participation participation;
    private int currentQuestionIndex;
    private int lastQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participations);
        
        int pollId = getIntent().getIntExtra("poll_id", 0);
        
        // Getting the requested poll
        webGateway = new WebGateway(this);
        poll = webGateway.getPoll(pollId);
        
        if (poll == null) {
            Toast.makeText(this, "Error displaying the requested poll", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, PollsActivity.class));
        }
        questions = poll.getQuestions();
        
        // Setting the state variables
        currentQuestionIndex = 0;
        lastQuestionIndex = poll.getQuestions().size() - 1;
        
        // Displaying the GUI
        displayGui();
        
    }
    
    private void displayGui() {
        
        // Current Question
        Question question = questions.get(currentQuestionIndex);
        
        // Setting the Poll title
        TextView pollTitle = (TextView) findViewById(R.id.text_view_participations_title);
        pollTitle.setText(poll.getTitle());
        
        // Setting the Question text
        TextView questionText = (TextView) findViewById(R.id.text_view_participations_question_text);
        questionText.setText(question.getText());
        
        // Setting Button text and event listeners
        setButtons();
        
    }
    
    private void setButtons() {
        Button backButton = (Button) findViewById(R.id.button_participations_back);
        
        backButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex != 0) {
                    currentQuestionIndex--;
                    displayGui();
                }
                
            }
        });
        
        Button nextButton = (Button) findViewById(R.id.button_participations_next);
        
        if (currentQuestionIndex < lastQuestionIndex) {
            nextButton.setText("Next");
        } else {
            nextButton.setText("Finish");
        }
        
        nextButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < lastQuestionIndex) {
                    currentQuestionIndex++;
                    displayGui();
                } else {
                    finishParticipation();
                }
                
            }
        });
    }
    
    private void finishParticipation() {
        Toast.makeText(this, "Finish button pressed", Toast.LENGTH_SHORT).show();
    }
    
    public void cancel(View v) {
        startActivity(new Intent(this, PollsActivity.class));
    }
}
