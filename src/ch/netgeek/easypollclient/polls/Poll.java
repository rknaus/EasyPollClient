package ch.netgeek.easypollclient.polls;

import java.util.ArrayList;
import java.util.Date;

public class Poll {
    
    private int pollId;
    private String title;
    private Date publishedAt;
    private Date closedAt;
    private Date createdAt;
    private Date updatedAt;
    private String category;
    private int userId;
    private String userName;
    ArrayList<Question> questions;
    
    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<Question>();
        }
        questions.add(question);
    }
    
    public int getPollId() {
        return pollId;
    }
    
    public void setPollId(int pollId) {
        this.pollId = pollId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Date getPublishedAt() {
        return publishedAt;
    }
    
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    public Date getClosedAt() {
        return closedAt;
    }
    
    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }
    
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    
}
