package src.blog.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.blog.domain.entity.Article;
import src.blog.services.BlogService;

public class ArticleListItemController {

    private String articleId = null;
    private BlogService blogService = BlogService.getInstance();

    @FXML
    private AnchorPane articleItemContainer;

    @FXML
    private Text articleTitle;

    @FXML
    private Text articleAuthorDate;

    @FXML
    private Button articleViewButton;

    @FXML
    void viewArticle(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/blog/gui/fxml/ArticlePage.fxml"));
        Parent articlePageParent = loader.load();
        Scene articlePageScene = new Scene(articlePageParent);
        ArticlePageController articlePageController = loader.getController();
        articlePageController.init(articleId);
        Stage window = (Stage)articleItemContainer.getScene().getWindow();
        window.setTitle("Article");
        window.setScene(articlePageScene);
        window.show();
    }

    public void init(String articleId) {
        this.articleId = articleId;
        Article article = blogService.getArticleById(articleId);
        System.out.println(articleId);
        articleTitle.setText(article.getTitle());
        articleAuthorDate.setText("posted by " + article.getAuthor().getDisplayName() + ", at " + article.getPostDate());
    }

}
