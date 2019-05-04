package src.blog.domain.repository;

import src.blog.domain.entity.Article;
import src.blog.domain.entity.PollArticle;

import java.util.*;

public class ArticleRepository {
    private Map<String, Article> articles = new HashMap<>();

    public Article findArticleByTitle (String title) {
        for(Map.Entry<String, Article> entry : articles.entrySet()) {
            Article article = entry.getValue();
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

        String id = UUID.randomUUID().toString();
        newArticle.setId(id);
        newArticle.setPostDate(new Date());

        articles.put(id, newArticle);

        return newArticle;
    }

    public PollArticle addNewPoll (PollArticle newArticle) {
        PollArticle articleWithSameTitle = (PollArticle)findArticleByTitle(newArticle.getTitle());

        if(articleWithSameTitle != null) {
            System.out.println("There's already an article with this title!");
            return null;
        }

        String id = UUID.randomUUID().toString();
        newArticle.setId(id);
        newArticle.setPostDate(new Date());

        articles.put(id, newArticle);

        return newArticle;
    }

    public Map<String, Article> getAllArticles () {
        return articles;
    }

    public Article getArticleById(String id) {
//        for(Map.Entry<String, Article> entry : articles.entrySet()) {
//            Article article = entry.getValue();
//            if(article.getId().equals(id)) return article;
//        }
        return articles.get(id);
//        return null;
    }

    public Article getArticleByTitle(String title) {
        for(Map.Entry<String, Article> entry : articles.entrySet()) {
            Article article = entry.getValue();
            if(article.getTitle().equals(title)) return article;
        }
        return null;
    }

    public Article loadArticle (Article newArticle) {
        articles.put(newArticle.getId(), newArticle);
        return newArticle;
    }
}
