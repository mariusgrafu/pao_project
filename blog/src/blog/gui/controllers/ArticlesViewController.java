package src.blog.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.blog.domain.entity.Article;
import src.blog.domain.entity.Role;
import src.blog.domain.entity.User;
import src.blog.services.BlogService;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ArticlesViewController implements Initializable {

    BlogService blogService = BlogService.getInstance();
    User loggedUser = null;

    @FXML
    private HBox topHbox;

    @FXML
    private Menu usernameMenuText;

    @FXML
    private AnchorPane simpleListContainer;

    @FXML
    private Text simpleListTitle;

    @FXML
    private ListView<String> simpleList;

    @FXML
    private AnchorPane articlesListContainer;

    @FXML
    private ListView<AnchorPane> articlesList;

    public void initialize(URL location, ResourceBundle resourceBundle){
        loggedUser = blogService.getLoggedUser();
        usernameMenuText.setText(loggedUser.getDisplayName());
        try {
            _showArticlesList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logout(ActionEvent event) throws Exception{
        blogService.setLoggedUser(null);
        Parent loginParent = FXMLLoader.load(getClass().getClassLoader().getResource("src/blog/gui/fxml/loginScene.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage)topHbox.getScene().getWindow();
        window.setTitle("Login Page");
        window.setScene(loginScene);
        window.show();
    }


    @FXML
    void showMembersList(ActionEvent event) {
        articlesListContainer.setVisible(false);
        simpleListContainer.setVisible(true);
        simpleListTitle.setText("Members");
        Map<String, User> members = blogService.getUsers();

        simpleList.getItems().clear();

        members.forEach((k, v) -> {
            simpleList.getItems().add(v.toString());
        });
    }

    @FXML
    void showRolesList(ActionEvent event) {
        articlesListContainer.setVisible(false);
        simpleListContainer.setVisible(true);
        simpleListTitle.setText("Roles");
        Map<String, Role> roles = blogService.getRoles();

        simpleList.getItems().clear();

        roles.forEach((k, v) -> {
            simpleList.getItems().add(v.toString());
        });
    }

    void _showArticlesList() throws Exception {
        articlesListContainer.setVisible(true);
        simpleListContainer.setVisible(false);

        articlesList.getItems().clear();

        Map<String, Article> articles = blogService.getArticles();
        articles.forEach((k, v) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/blog/gui/fxml/articleListItem.fxml"));
            AnchorPane listItem = null;
            try {
                listItem = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArticleListItemController articleListItemController = loader.getController();
            articleListItemController.init(k);

            articlesList.getItems().add(listItem);
        });

    }

    @FXML
    void showArticlesList(ActionEvent event) throws Exception {
        _showArticlesList();

    }

}
