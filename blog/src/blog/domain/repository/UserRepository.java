package src.blog.domain.repository;

import src.blog.domain.entity.Article;
import src.blog.domain.entity.Notification;
import src.blog.domain.entity.User;
import src.blog.tool.Validator;

import java.util.ArrayList;
import java.util.Date;

public class UserRepository {
    private ArrayList<User> users = new ArrayList<User>();

    private int lastId;

    public User findUserByName(String username) {
        username = username.toLowerCase();
        for(User user : users) {
            if(user.getUsername().toLowerCase().equals(username)) return user;
        }
        return null;
    }

    public User addNewUser(User newUser) {
        // check to see if there's already an user with the same username
        User userWithSameName = findUserByName(newUser.getUsername());

        if(userWithSameName != null) {
            System.out.println("There's already an user with the same name!");
            return null;
        }
        // check to see if email is valid
        if(!Validator.checkEmail(newUser.getEmail())) {
            System.out.println("Invalid email address!");
            return null;
        }

        newUser.setId(lastId++);
        newUser.setRegisterDate(new Date());

        users.add(newUser);

        return newUser;
    }

    public User getUserByNameAndPass (String username, String password) {
        username = username.toLowerCase();
        for(User user : users) {
            if(user.getUsername().toLowerCase().equals(username)) {
                if(user.getPassword().equals(password)) return user;
                return null;
            }
        }

        return null;
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

    public void newArticleNotification(Article newArticle) {
        int authorId = newArticle.getAuthor().getId();
        String notificationContent = "A new article: \"" + newArticle.getTitle() + "\"!";
        for(User user : users) {
            if(user.getId() != authorId && user.getRole().getPermissions().getSeeArticle()){
                user.addNotification(new Notification(notificationContent));
            }
        }
    }
}
