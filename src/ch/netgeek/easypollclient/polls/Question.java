package ch.netgeek.easypollclient.polls;

import java.util.ArrayList;

public class Question {
    
    private int questionId;
    private String text;
    private String kind;
    private ArrayList<Option> options;
    
    public Question(int questionId, String text, String kind) {
        setQuestionId(questionId);
        setText(text);
        setKind(kind);
    }
    
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
    
    public ArrayList<Option> getOptions() {
        return options;
    }
    
    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

}
