package ch.netgeek.easypollclient.results;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.netgeek.easypollclient.EasyPollClientActivity;
import ch.netgeek.easypollclient.R;
import ch.netgeek.easypollclient.polls.Option;
import ch.netgeek.easypollclient.polls.Poll;
import ch.netgeek.easypollclient.polls.Question;
import ch.netgeek.easypollclient.web.WebGateway;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends ListActivity {
    
    private WebGateway webGateway;
    private Poll poll;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        
        int pollId = getIntent().getIntExtra("poll_id", 0);
        webGateway = new WebGateway(this);
        poll = webGateway.getResults(pollId);
        
        if (poll == null) {
            Toast.makeText(this, "Invalid Poll id!", Toast.LENGTH_LONG).show();
        } else {
            questions = poll.getQuestions();
            setContent();
        }
    }
    
    private void setContent() {
        setListAdapter(new MyCustomAdapter(ResultsActivity.this, R.layout.results_list_item, questions));
        
        TextView title = (TextView) findViewById(R.id.text_view_results_title);
        title.setText(poll.getTitle());
        
        TextView pollCategory = (TextView) findViewById(R.id.text_view_results_poll_category_text);
        pollCategory.setText(poll.getCategory());
        
        TextView pollCreatedBy = (TextView) findViewById(R.id.text_view_results_poll_created_by_text);
        pollCreatedBy.setText(poll.getUserName());
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        TextView pollPublishedAt = (TextView) findViewById(R.id.text_view_results_poll_published_at_text);
        pollPublishedAt.setText(formatter.format(poll.getPublishedAt()));
        
        TextView pollClosedAt = (TextView) findViewById(R.id.text_view_results_poll_closed_at_text);
        Date closedAt = poll.getClosedAt();
        if (closedAt != null) {
            pollClosedAt.setText(formatter.format(poll.getClosedAt()));
        }
        
        TextView pollQuestions = (TextView) findViewById(R.id.text_view_results_poll_questions_text);
        pollQuestions.setText(String.valueOf(poll.getQuestionsCount()));
        
        TextView pollParticipations = (TextView) findViewById(R.id.text_view_results_poll_participations_text);
        pollParticipations.setText(String.valueOf(poll.getParticipationsCount()));
        
    }
    
    public class MyCustomAdapter extends ArrayAdapter<Question> {
        
        public MyCustomAdapter(Context context, int textViewResourceId, List<Question> objects) {
            super(context, textViewResourceId, objects);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.results_list_item, parent, false);
            
            Question question = questions.get(position);
            
            // Setting the fields values
            TextView itemTitle = (TextView) row.findViewById(R.id.text_view_results_item_title);
            itemTitle.setText(question.getText());
            
            TextView itemKind = (TextView) row.findViewById(R.id.text_view_results_item_kind);
            itemKind.setText("Kind: " + question.getKind());
            
            ArrayList<Option> options = question.getOptions();
            TableLayout optionsTable = (TableLayout) row.findViewById(R.id.answers_table);
            
            TableRow titleRow = new TableRow(getContext());
            
            TextView optionTextTitle = new TextView(getContext());
            optionTextTitle.setText("Text");
            titleRow.addView(optionTextTitle);
            
            TextView optionCountTitle = new TextView(getContext());
            optionCountTitle.setText("# of answers");
            titleRow.addView(optionCountTitle);
            
            TextView optionPercentTitle = new TextView(getContext());
            optionPercentTitle.setText("% of answers");
            titleRow.addView(optionPercentTitle);
            
            optionsTable.addView(titleRow);
            
            for (int i = 0; i < options.size(); i++) {
                Option option = options.get(i);
                TableRow tableRow = new TableRow(getContext());
                
                TextView optionText = new TextView(getContext());
                optionText.setText(option.getText());
                tableRow.addView(optionText);
                
                TextView optionCount = new TextView(getContext());
                optionCount.setText(String.valueOf(option.getAnswersCount()));
                tableRow.addView(optionCount);
                
                TextView optionPercent = new TextView(getContext());
                optionPercent.setText(String.valueOf(option.getAnswersPercent()));
                tableRow.addView(optionPercent);
                
                optionsTable.addView(tableRow);
            }
            
            // returning the current row
            return row;
        }
    }
    
    public void back(View v) {
        Intent intent = new Intent(this, EasyPollClientActivity.class);
        intent.putExtra("tab_id", 1);
        startActivity(intent);
    }
}
