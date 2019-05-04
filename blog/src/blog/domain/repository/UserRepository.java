package src.blog.domain.repository;

import src.blog.domain.entity.Article;
import src.blog.domain.entity.Notification;
import src.blog.domain.entity.User;
import src.blog.tool.Validator;

import java.util.*;

public class UserRepository {
    private Map<String, User> users = new HashMap<>();

    public User findUserByName(String username) {
        username = username.toLowerCase();
        for(Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
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

        String id = UUID.randomUUID().toString();
        newUser.setId(id);
        newUser.setRegisterDate(new Date());

        users.put(id, newUser);

        return newUser;
    }

    public User loadUser (User newUser) {
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    public User getUserById (String id) {
        return users.get(id);
    }

    public User getUserByNameAndPass (String username, String password) {
        username = username.toLowerCase();
        for(Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            if(user.getUsername().toLowerCase().equals(username)) {
                if(user.getPassword().equals(password)) return user;
                return null;
            }
        }

        return null;
    }

    public Map<String, User> getAllUsers() {
        return users;
    }

    public void newArticleNotification(Article newArticle) {
        String authorId = newArticle.getAuthor().getId();
        String notificationContent = "A new article: \"" + newArticle.getTitle() + "\"!";
        for(Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            if(!user.getId().equals(authorId) && user.getRole().getPermissions().getSeeArticle()){
                user.addNotification(new Notification(notificationContent));
            }
        }
    }
}
