package src.blog.domain.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Article {
    private String id;

    private String title;
    private String content;
    private User author;

    private Date postDate;

    private Map<Date, Comment> comments = new TreeMap<>(
            new Comparator<Date>() {
                @Override
                public int compare(Date o1, Date o2) {
                    return o1.after(o2) ? 1 : -1;
                }
            }
    );

    private static final DateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Article(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public static Article getArticleFromCSV (String line) {
        String[] values = line.split(",");
        String id = values[1];
        String title = values[2];
        String content = values[3];
        Date postDate = null;
        try {
            postDate = date_format.parse(values[5]);
        } catch (ParseException e) {
            postDate = new Date();
            e.printStackTrace();
        }

        Article newArticle = new Article(title, content, null);
        newArticle.setId(id);
        newArticle.setPostDate(postDate);

        return newArticle;
    }

    protected String dataForCSV () {
        return "," + id +
                "," + title +
                "," + content +
                "," + author.getId() +
                "," + date_format.format(postDate);
    }

    public String toCSV() {
        return "0" + // 0 for simple article, 1 for poll
                dataForCSV();
    }

    @Override
    public String toString() {
        return
//                "[" + id + "] " +
                "\'" + title + '\'' +
                ", by " + author.getDisplayName() +
                ", " + postDate +
                ", " + comments.size() + " comments";
    }

    private void printComments () {
        for(Map.Entry<Date, Comment> entry : comments.entrySet()) {
            Comment comment = entry.getValue();
            System.out.println("┌╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴┐");
            System.out.println(comment);
            System.out.println("└╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴╴┘");
        }
    }

    protected void printArticleSection (boolean compactView) {
        System.out.println("\'" + title + "\'");
        if(!compactView){
            System.out.println("by " + author.getDisplayName() + ", " + postDate);
        }
        System.out.println();
        System.out.println(content);
        System.out.println();
    }

    protected void printCommentSection () {
        System.out.println(comments.size() + " comments");

        printComments();
    }

    public void printArticle (boolean compactView) {
        printArticleSection(compactView);
        printCommentSection();
    }

    public Comment addNewComment (Comment newComment) {
        Date postDate = new Date();
        newComment.setPostDate(postDate);
        comments.put(postDate, newComment);
        User author = newComment.getAuthor();
        if(!author.getId().equals(this.author.getId())) this.author.addNotification(new Notification("New Comment!"));
        return newComment;
    }
}
