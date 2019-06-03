package src.blog.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.blog.domain.entity.User;
import src.blog.services.BlogService;

public class LoginController {

    BlogService blogService = BlogService.getInstance();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginSubmit;

    @FXML
    private Pane errorBox;

    @FXML
    private Text errorText;

    @FXML
    void submitLogin(ActionEvent event) throws Exception{
        errorBox.setVisible(false);
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();
        User loggedUser = blogService.getUserByNameAndPass(enteredUsername, enteredPassword);
        if(loggedUser != null) {
            blogService.setLoggedUser(loggedUser);
            Parent articlesViewParent = FXMLLoader.load(getClass().getClassLoader().getResource("src/blog/gui/fxml/ArticlesView.fxml"));
            Scene articlesViewScene = new Scene(articlesViewParent);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Blog Page");
            window.setScene(articlesViewScene);
            window.show();
        } else {
            errorText.setText("User not found!");
            errorBox.setVisible(true);
            usernameField.setText("");
            passwordField.setText("");
        }
    }

}
