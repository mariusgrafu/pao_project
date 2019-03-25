package blog;

import java.util.ArrayList;
import java.util.Date;

public class Article {
    private int id;

    private String title;
    private String content;
    private User author;

    private Date postDate;

    private ArrayList<Comment> comments = new ArrayList<Comment>();

    static private int count;
}
