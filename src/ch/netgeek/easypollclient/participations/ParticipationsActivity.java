package ch.netgeek.easypollclient.participations;

import java.util.ArrayList;

import ch.netgeek.easypollclient.R;
import ch.netgeek.easypollclient.web.WebGateway;
import ch.netgeek.easypollclient.polls.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
        
        // Setting the Options in linear_layout_participations_question_options
        ArrayList<Option> options = question.getOptions();
        LinearLayout optionsLayout = (LinearLayout) findViewById(R.id.linear_layout_participations_question_options);
        optionsLayout.removeAllViews();
        if (question.getKind().equals("multiple choice")) {
            for (int i = 0; i < options.size(); i++) {
                Option option = options.get(i);
                CheckBox checkBox = new CheckBox(this);
                checkBox.setId(i + 1000);
                checkBox.setTag("option " + String.valueOf(i));
                checkBox.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                checkBox.setText(option.getText());
                checkBox.setChecked(option.isChecked());
                optionsLayout.addView(checkBox);
            }
        } else {
            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setId(999);
            radioGroup.setTag("radio group " + 999);
            radioGroup.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            for (int i = 0; i < options.size(); i++) {
                Option option = options.get(i);
                RadioButton radioButton = new RadioButton(this);
                radioButton.setId(i + 1000);
                radioButton.setTag("option " + String.valueOf(i));
                radioButton.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
                radioButton.setText(option.getText());
                radioButton.setChecked(option.isChecked());
                radioGroup.addView(radioButton);
            }
            optionsLayout.addView(radioGroup);
        }
        
        // Setting Button text and event listeners
        setButtons();
        
    }
    
    private void setButtons() {
        Button backButton = (Button) findViewById(R.id.button_participations_back);
        
        backButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex != 0) {
                    back();
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
                    next();
                } else {
                    finishParticipation();
                }
                
            }
        });
    }
    
    private boolean validOptionState() {
        boolean state = false;
        Question question = questions.get(currentQuestionIndex);
        ArrayList<Option> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            if (question.getKind().equals("multiple choice")) {
                CheckBox checkBox = (CheckBox) findViewById(i + 1000);
                if (checkBox.getTag().equals("option " + String.valueOf(i))) {
                    if (checkBox.isChecked()) {
                        state = true;
                        break;
                    }
                }
            } else {
                RadioButton radioButton = (RadioButton) findViewById(i + 1000);
                if (radioButton.getTag().equals("option " + String.valueOf(i))) {
                    if (radioButton.isChecked()) {
                        state = true;
                        break;
                    }
                }
            }
        }
        
        return state;
    }
    
    private void saveOptionState() {
        Question question = questions.get(currentQuestionIndex);
        ArrayList<Option> options = question.getOptions();
        for (int i = 0; i < options.size(); i++) {
            Option option = options.get(i);
            if (question.getKind().equals("multiple choice")) {
                CheckBox checkBox = (CheckBox) findViewById(i + 1000);
                if (checkBox.getTag().equals("option " + String.valueOf(i))) {
                    option.setChecked(checkBox.isChecked());
                }
            } else {
                RadioButton radioButton = (RadioButton) findViewById(i + 1000);
                if (radioButton.getTag().equals("option " + String.valueOf(i))) {
                    option.setChecked(radioButton.isChecked());
                }
            }
        }
    }
    
    private void back() {
        saveOptionState();
        currentQuestionIndex--;
        displayGui();
    }
    
    private void next() {
        if (validOptionState()) {
            saveOptionState();
            currentQuestionIndex++;
            displayGui();
        } else {
            Toast.makeText(this, "Please make a selection", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void finishParticipation() {
        if (validOptionState()) {
            saveOptionState();
            if (webGateway.postParticipation(poll)) {
                Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, PollsActivity.class));
            } else {
                Toast.makeText(this, "Error saving the results...", Toast.LENGTH_SHORT).show();
            }
            
        } else {
            Toast.makeText(this, "Please make a selection", Toast.LENGTH_SHORT).show();
        }
    }
    
    public void cancel(View v) {
        startActivity(new Intent(this, PollsActivity.class));
    }
}
