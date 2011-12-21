package ch.netgeek.easypollclient.polls;

import java.util.ArrayList;
import java.util.Date;

public class Question {
    
    private int questionId;
    private int pollId;
    private String text;
    private String kind;
    private Date createdAt;
    private Date updatedAt;
    private ArrayList<Option> options;
    
    public void addOption(Option option) {
        if (options == null) {
            options = new ArrayList<Option>();
        }
        options.add(option);
    }
    
    public int getQuestionId() {
        return questionId;
    }
    
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
    
    public int getPollId() {
        return pollId;
    }
    
    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getKind() {
        return kind;
    }
    
    public void setKind(String kind) {
        this.kind = kind;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public ArrayList<Option> getOptions() {
        return options;
    }
    
    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

}
