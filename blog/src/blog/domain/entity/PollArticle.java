package src.blog.domain.entity;

import java.util.ArrayList;

public class PollArticle extends Article {
    private ArrayList<PollOption> options = new ArrayList<PollOption>();

    public PollArticle(String title, String content, User author) {
        super(title, content, author);
    }

    public ArrayList<PollOption> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<PollOption> options) {
        this.options = options;
    }

    public PollOption getPollOptionByTitle (String title) {
        for(PollOption option : options) {
            if(option.getTitle().equals(title)) return option;
        }

        return null;
    }

    public PollOption addNewOption (PollOption newPollOption) {
        PollOption optionWithSameName = getPollOptionByTitle(newPollOption.getTitle());
        if(optionWithSameName != null) {
            System.out.println("There's already an option with this title!");
            return null;
        }

        options.add(newPollOption);

        return newPollOption;
    }

    private void printPollSection () {
        System.out.println("Poll options:");
        System.out.println("┌╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴┐");
        for(int i = 0; i < options.size(); ++i) {
            System.out.println("#" + (i + 1) + " " + options.get(i));
        }
        System.out.println("└╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴┘");
    }

    public void printArticle (boolean compactView) {
        super.printArticleSection(compactView);
        printPollSection();
        super.printCommentSection();
    }

    public PollOption voteOptionById (int id, User voter) {
        if(id < 0 || id >= options.size()) {
            return null;
        }
        if(options.get(id).addVote(voter)) {
            if(voter.getId() != this.getAuthor().getId()) {
                this.getAuthor().addNotification(new Notification("New Vote!"));
            }
            return options.get(id);
        }

        return null;
    }
}
