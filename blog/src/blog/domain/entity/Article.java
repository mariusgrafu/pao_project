package src.blog.domain.entity;

import java.util.ArrayList;
import java.util.Date;

public class Article {
    private int id;

    private String title;
    private String content;
    private User author;

    private Date postDate;

    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public Article(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "[" + id + "] " +
                "\'" + title + '\'' +
                ", by " + author.getDisplayName() +
                ", " + postDate +
                ", " + comments.size() + " comments";
    }

    private void printComments () {
        for(Comment comment : comments) {
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
        newComment.setPostDate(new Date());
        comments.add(newComment);
        User author = newComment.getAuthor();
        if(author.getId() != this.author.getId()) this.author.addNotification(new Notification("New Comment!"));
        return newComment;
    }
}
