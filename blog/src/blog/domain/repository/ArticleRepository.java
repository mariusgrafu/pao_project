package src.blog.domain.repository;

import src.blog.domain.entity.Article;
import src.blog.domain.entity.PollArticle;

import java.util.ArrayList;
import java.util.Date;

public class ArticleRepository {
    private ArrayList<Article> articles = new ArrayList<Article>();

    private int lastId;

    public Article findArticleByTitle (String title) {
        for(Article article : articles) {
            if(article.getTitle().equals(title)) return article;
        }
        return null;
    }

    public Article addNewArticle (Article newArticle) {
        Article articleWithSameTitle = findArticleByTitle(newArticle.getTitle());

        if(articleWithSameTitle != null) {
            System.out.println("There's already an article with this title!");
            return null;
        }

        newArticle.setId(lastId++);
        newArticle.setPostDate(new Date());

        articles.add(newArticle);

        return newArticle;
    }

    public PollArticle addNewPoll (PollArticle newArticle) {
        PollArticle articleWithSameTitle = (PollArticle)findArticleByTitle(newArticle.getTitle());

        if(articleWithSameTitle != null) {
            System.out.println("There's already an article with this title!");
            return null;
        }

        newArticle.setId(lastId++);
        newArticle.setPostDate(new Date());

        articles.add(newArticle);

        return newArticle;
    }

    public ArrayList<Article> getAllArticles () {
        return articles;
    }

    public Article getArticleById(int id) {
        for(Article article : articles) {
            if(article.getId() == id) return article;
        }
        return null;
    }

    public Article getArticleByTitle(String title) {
        for(Article article : articles) {
            if(article.getTitle().equals(title)) return article;
        }
        return null;
    }
}
