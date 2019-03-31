package src.blog.domain.entity;

import java.util.ArrayList;

public class PollOption {
    private String title;
    private ArrayList<Integer> voters = new ArrayList<Integer>();

    public PollOption(String title) {
        this.title = title;
    }

    public int getVotes() {
        return voters.size();
    }

    public boolean addVote (User voter) {
        int voterId = voter.getId();
        int found = voters.indexOf(voterId);
        if(found != -1) {
            System.out.println("You already voted!");
            return false;
        }
        this.voters.add(voterId);

        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        int votes = getVotes();
        return "\'" + title + '\'' +
                ", " + votes +
                " vote" + ((votes != 1)?"s":"");
    }
}
