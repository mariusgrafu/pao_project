package src.blog.domain.entity;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

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
//            System.out.println("There's already an option with this title!");
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
            if(!voter.getId().equals(this.getAuthor().getId())) {
                this.getAuthor().addNotification(new Notification("New Vote!"));
            }
            return options.get(id);
        }

        return null;
    }

    public static Article getArticleFromCSV (String line) {
        Article newArticle = Article.getArticleFromCSV(line);
        PollArticle newPollArticle = new PollArticle(newArticle.getTitle(), newArticle.getContent(), newArticle.getAuthor());
        newPollArticle.setPostDate(newArticle.getPostDate());
        String[] values = line.split(",");
        for(int i = 6; i < values.length; ++i) {
            PollOption newPollOption = PollOption.getOptionFromCSV(values[i]);
            newPollArticle.addNewOption(newPollOption);
        }
        return newPollArticle;
    }

    public static Article getArticleFromResultSet (ResultSet resultSet) throws Exception {
        Article newArticle = Article.getArticleFromResultSet(resultSet);
        PollArticle newPollArticle = new PollArticle(newArticle.getTitle(), newArticle.getContent(), newArticle.getAuthor());
        newPollArticle.setPostDate(newArticle.getPostDate());
        newPollArticle.setId(newArticle.getId());
        String[] values = resultSet.getString("optionsCSV").split(",");
        for(int i = 0; i < values.length; ++i) {
            PollOption newPollOption = PollOption.getOptionFromCSV(values[i]);
            newPollArticle.addNewOption(newPollOption);
        }
        return newPollArticle;
    }

    public String optionsToCSV () {
        StringBuilder optionsCSV = new StringBuilder();
        for(int i = 0; i < options.size(); ++i) {
            optionsCSV.append("," + options.get(i).toCSV());
        }

        return optionsCSV.toString();
    }

    public String toCSV() {
        String optionsCSV = optionsToCSV();
        return "1" + // 0 for simple article, 1 for poll
                dataForCSV() +
                optionsCSV;
    }
}
