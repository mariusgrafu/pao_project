package src.blog.domain.entity;

import java.util.Date;

public class Notification {
    private String content;
    private boolean seen;

    private Date postDate;

    public Notification(String content) {
        this.content = content;

        this.seen = false;
        this.postDate = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        String isNew = "[NEW] ";
        if(seen) isNew = "";
        return isNew + content +
                ", " + postDate ;
    }
}
