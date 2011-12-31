package ch.netgeek.easypollclient.polls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ch.netgeek.easypollclient.R;
import ch.netgeek.easypollclient.participations.ParticipationsActivity;
import ch.netgeek.easypollclient.web.WebGateway;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UnansweredPollsActivity extends ListActivity {
    
    private WebGateway webGateway;
    private ArrayList<Poll> polls;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polls);
        
        // Getting the list content
        webGateway = new WebGateway(this);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        polls = webGateway.getUnansweredPolls();
        
        if (polls == null) {
            polls = new ArrayList<Poll>();
        }
        
        setListAdapter(new MyCustomAdapter(UnansweredPollsActivity.this, R.layout.polls_list_item, polls));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, ParticipationsActivity.class);
        intent.putExtra("poll_id", polls.get(position).getPollId());
        startActivity(intent);
    }
    
    public class MyCustomAdapter extends ArrayAdapter<Poll> {

        public MyCustomAdapter(Context context, int textViewResourceId, List<Poll> objects) {
            super(context, textViewResourceId, objects);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.polls_list_item, parent, false);
            
            Poll poll = polls.get(position);
            
            // Setting the fields values
            TextView title = (TextView) row.findViewById(R.id.text_view_polls_item_title);
            title.setText(poll.getTitle());
            
            TextView category = (TextView) row.findViewById(R.id.text_view_polls_item_category);
            category.setText("Category: " + poll.getCategory());
            
            TextView publishedAt = (TextView) row.findViewById(R.id.text_view_polls_item_published_at);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
            publishedAt.setText("Date: " + formatter.format(poll.getPublishedAt()));
            
            TextView questionsCount = (TextView) row.findViewById(R.id.text_view_polls_item_questions_count);
            questionsCount.setText("Questions: " + String.valueOf(poll.getQuestionsCount()));
            
            TextView participationsCount = (TextView) row.findViewById(R.id.text_view_polls_item_participations_count);
            participationsCount.setText("Participations: " + String.valueOf(poll.getParticipationsCount()));
            
            // returning the current row
            return row;
        }
        
    }
    
}
