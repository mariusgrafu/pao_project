package src.blog.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.blog.domain.entity.Article;
import src.blog.domain.entity.Comment;
import src.blog.services.BlogService;

public class ArticlePageController {

    private String articleId = null;
    private BlogService blogService = BlogService.getInstance();

    @FXML
    private HBox topHBox;

    @FXML
    private Text articleTitle;

    @FXML
    private Text articleAuthorDate;

    @FXML
    private Text articleContent;

    @FXML
    private TextField newCommentField;

    @FXML
    private ListView<String> commentsList;

    @FXML
    void goBack(ActionEvent event) throws Exception{
        Parent articlesParent = FXMLLoader.load(getClass().getClassLoader().getResource("src/blog/gui/fxml/ArticlesView.fxml"));
        Scene articlesScene = new Scene(articlesParent);
        Stage window = (Stage)topHBox.getScene().getWindow();
        window.setTitle("Login Page");
        window.setScene(articlesScene);
        window.show();
    }

    @FXML
    void submitNewComment(ActionEvent event) {
        Article article = blogService.getArticleById(articleId);
        String newCommentMsg = newCommentField.getText();
        Comment newComment = new Comment(newCommentMsg, blogService.getLoggedUser());
        article.addNewComment(newComment);

        newCommentField.setText("");

        refreshCommentsList();
    }

    private void refreshCommentsList () {
        Article article = blogService.getArticleById(articleId);
        commentsList.getItems().clear();
        article.getCommentsMap().forEach((k, v) -> {
            commentsList.getItems().add(v.toString());
        });
    }

    public void init (String articleId) {
        this.articleId = articleId;
        Article article = blogService.getArticleById(articleId);

        articleTitle.setText(article.getTitle());
        articleAuthorDate.setText("posted by " + article.getAuthor().getDisplayName() + ", at " + article.getPostDate());
        articleContent.setText(article.getContent());

        refreshCommentsList();
    }

}
