package ch.netgeek.easypollclient.participations;

import ch.netgeek.easypollclient.polls.Poll;

public class Participation {

    private Poll poll;
    
    public Participation(Poll poll) {
        setPoll(poll);
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    
}
