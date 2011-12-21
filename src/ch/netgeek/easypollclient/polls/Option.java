package ch.netgeek.easypollclient.polls;

import java.util.Date;

public class Option {
    
    private int optionId;
    private int questionId;
    private String text;
    private Date createdAt;
    private Date updatedAt;
    
    public int getOptionId() {
        return optionId;
    }
    
    public void setOptionId(int optionId) {
        this.optionId = optionId;
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

}
