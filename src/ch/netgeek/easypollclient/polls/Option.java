package ch.netgeek.easypollclient.polls;

public class Option {
    
    private int optionId;
    private String text;
    
    public Option(int optionId, String text) {
        setOptionId(optionId);
        setText(text);
    }
    
    public int getOptionId() {
        return optionId;
    }
    
    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
}
