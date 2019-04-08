package src.blog.domain.entity;

import java.util.Date;

public class Comment {
    private User author;
    private String content;
    private Date postDate;

    public Comment(String content, User author) {
        this.author = author;
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return author.getDisplayName()  +
                ", " + postDate + "\n" +
                content;
    }
}
