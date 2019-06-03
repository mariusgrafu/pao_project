package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.blog.services.BlogService;

public class StartGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BlogService blogService = BlogService.getInstance("Marius", "asd", "marius@email.com");

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/blog/gui/fxml/loginScene.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    @Override
    public void stop() {
        BlogService blogService = BlogService.getInstance();
        blogService.save();
    }
}
