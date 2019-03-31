package src.blog.services;

import src.blog.domain.entity.Article;
import src.blog.domain.entity.PollArticle;
import src.blog.domain.entity.Role;
import src.blog.domain.entity.User;
import src.blog.domain.repository.ArticleRepository;
import src.blog.domain.repository.RoleRepository;
import src.blog.domain.repository.UserRepository;
import src.blog.tool.PermissionsBuilder;

import java.util.ArrayList;

public class BlogService {
    private final ArticleRepository articleRepository = new ArticleRepository();
    private final UserRepository userRepository = new UserRepository();
    private final RoleRepository roleRepository = new RoleRepository();

    private static BlogService instance;

    private BlogService (String admin_username, String admin_password, String admin_email) {

        // creating default roles (Administrator and Member)
        Role adminRole = roleRepository.addNewRole(new Role("Administrator", new PermissionsBuilder().setAll().build(), false, true));
        roleRepository.addNewRole(new Role("Member", new PermissionsBuilder().canSeeArticle().build(), true, false));

        // creating the first account (admin of the platform)
        User admin = userRepository.addNewUser(new User(admin_username, admin_password, admin_email, adminRole));

        // creating the first article (a demo)
        articleRepository.addNewArticle(new Article("Your First Article!!", "This is the first article!\nSome kind of demo!", admin));

    }

    public static BlogService getInstance(String admin_username, String admin_password, String admin_email) {
        if(instance == null) {
            instance = new BlogService(admin_username, admin_password, admin_email);
        }
        return instance;
    }

    public static BlogService getInstance() {
        if(instance == null) {
            System.out.println("Blog Service must first be initialized by calling getInstance(admin_username, admin_password, admin_email)!");
        }
        return instance;
    }

    // methods

    public ArrayList<User> getUsers () {
        return userRepository.getAllUsers();
    }

    public ArrayList<Role> getRoles () { return roleRepository.getAllRoles(); }

    public void printUsers () {
        ArrayList<User> users = userRepository.getAllUsers();
        for(User user : users) {
            System.out.println(user);
        }
    }

    public void printRoles () {
        ArrayList<Role> roles = roleRepository.getAllRoles();
        for(Role role : roles) {
            System.out.println(role);
        }
    }

    public void printArticles () {
        ArrayList<Article> articles = articleRepository.getAllArticles();
        for(Article article : articles) {
            System.out.println(article);
        }
    }

    public User addNewUser (User newUser) {
        newUser.setRole(roleRepository.getDefaultRole());
        return userRepository.addNewUser(newUser);
    }

    public User getUserByNameAndPass (String username, String password) {
        return userRepository.getUserByNameAndPass(username, password);
    }

    public Role addNewRole (User loggedUser, Role newRole) {
        if(!loggedUser.isStaff()) return null;
        return roleRepository.addNewRole(newRole);
    }


    public Article getArticleByTitleOrId(String artName) {
        int artId = -1;
        try {
            artId = Integer.parseInt(artName);
        } catch (Exception e) {}

        if(artId != -1) return articleRepository.getArticleById(artId);
        return articleRepository.getArticleByTitle(artName);
    }

    public Article addNewArticle(Article newArticle) {
        if(!newArticle.getAuthor().getRole().getPermissions().getPostArticle()) return null;
        Article returnArticle = articleRepository.addNewArticle(newArticle);
        if(returnArticle != null) {
            userRepository.newArticleNotification(returnArticle);
        }
        return returnArticle;
    }
}
