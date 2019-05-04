package src.blog.domain.entity;

import java.util.ArrayList;

public class PollOption {
    private String title;
    private ArrayList<String> voters = new ArrayList<>();

    public PollOption(String title) {
        this.title = title;
    }

    public int getVotes() {
        return voters.size();
    }

    public boolean addVote (User voter) {
        String voterId = voter.getId();
        int found = voters.indexOf(voterId);
        if(found != -1) {
            System.out.println("You already voted!");
            return false;
        }
        this.voters.add(voterId);

        return true;
    }

    private void loadVoter (String voterId) {
        this.voters.add(voterId);
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

    public static PollOption getOptionFromCSV (String line) {
        String[] values = line.split("/");
        PollOption newPollOption = new PollOption(values[0]);
        if(values.length > 1) {
            String[] votersString = values[1].split(";");
            for(int i = 0; i < votersString.length; ++i) {
                newPollOption.loadVoter(votersString[i]);
            }
        }
        return newPollOption;
    }

    public String toCSV () {
        StringBuilder votersCSV = new StringBuilder();
        if(voters.size() != 0) votersCSV.append("/" + voters.get(0));
        for(int i = 1; i < voters.size(); ++i) {
            votersCSV.append(";" + voters.get(i));
        }
        return title + votersCSV;
    }
}
