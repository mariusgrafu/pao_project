package src.blog.services;

import src.blog.domain.entity.Article;
import src.blog.domain.entity.PollArticle;
import src.blog.domain.entity.Role;
import src.blog.domain.entity.User;
import src.blog.domain.repository.ArticleRepository;
import src.blog.domain.repository.RoleRepository;
import src.blog.domain.repository.UserRepository;
import src.blog.tool.PermissionsBuilder;

import javax.jws.soap.SOAPBinding;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class BlogService {
    private final ArticleRepository articleRepository = new ArticleRepository();
    private final UserRepository userRepository = new UserRepository();
    private final RoleRepository roleRepository = new RoleRepository();
    private final DBService dbService = DBService.getInstance("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7293788", "sql7293788", "cSgem3UMtK");

    private final AuditService auditService = AuditService.getInstance("pao_project/blog/src/csv/logs.csv");

    private User loggedUser = null;

    private static BlogService instance;

//    public void loadRoles () {
//        try {
//            Scanner scanner = new Scanner(new FileReader("pao_project/blog/src/csv/roles.csv"));
//            while(scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                Role newRole = Role.getRoleFromCSV(line);
//                roleRepository.loadRole(newRole);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    public void loadUsers () {
//        try {
//            Scanner scanner = new Scanner(new FileReader("pao_project/blog/src/csv/users.csv"));
//            while(scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                User newUser = User.getUserFromCSV(line);
//                newUser.setRole(roleRepository.getRoleById(line.split(",")[3]));
//                userRepository.loadUser(newUser);
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public void loadRoles () {
        try {
            Connection conn = dbService.connectToDB();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Roles");

            while(resultSet.next()) {
                Role newRole = Role.getRoleFromResultSet(resultSet);
                roleRepository.loadRole(newRole);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLoggedUser (User newLoggedUser) {
        loggedUser = newLoggedUser;
    }

    public User getLoggedUser () {
        return loggedUser;
    }

    public void loadUsers () {
        try {
            Connection conn = dbService.connectToDB();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while(resultSet.next()) {
                User newUser = User.getUserFromResultSet(resultSet);
                if(newUser == null) continue;
                newUser.setRole(roleRepository.getRoleById(resultSet.getString("roleId")));
                userRepository.loadUser(newUser);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadArticles () {
        try {
            Connection conn = dbService.connectToDB();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Articles");
            while(resultSet.next()) {
                Article newArticle = null;
                if(resultSet.getInt("type") == 0) {
                    newArticle = Article.getArticleFromResultSet(resultSet);
                } else {
                    newArticle = PollArticle.getArticleFromResultSet(resultSet);
                }

                newArticle.setAuthor(userRepository.getUserById(resultSet.getString("authorId")));
                articleRepository.loadArticle(newArticle);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void loadArticles () {
//        try {
//            Scanner scanner = new Scanner(new FileReader("pao_project/blog/src/csv/articles.csv"));
//            while(scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                Article newArticle = null;
//                if(line.split(",")[0].equals("0")) {
//                    newArticle = Article.getArticleFromCSV(line);
//                } else {
//                    newArticle = PollArticle.getArticleFromCSV(line);
//                }
//                newArticle.setAuthor(userRepository.getUserById(line.split(",")[4]));
//                articleRepository.loadArticle(newArticle);
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    private BlogService (String admin_username, String admin_password, String admin_email) {


        loadRoles();
        // creating default roles (Administrator and Member)
//        Role adminRole = roleRepository.addNewRole(new Role("Administrator", new PermissionsBuilder().setAll().build(), false, true));
//        roleRepository.addNewRole(new Role("Member", new PermissionsBuilder().canSeeArticle().build(), true, false));
        if(roleRepository.getAllRoles().size() == 0) {
            this._addNewRole(new Role("Administrator", new PermissionsBuilder().setAll().build(), false, true));
            this._addNewRole(new Role("Member", new PermissionsBuilder().canSeeArticle().build(), true, false));
        }

        // creating the first account (admin of the platform)
//        User admin = userRepository.addNewUser(new User(admin_username, admin_password, admin_email, adminRole));
        loadUsers();
        if(userRepository.getAllUsers().size() == 0) {
            Role adminRole = roleRepository.getRoleByTitle("Administrator");
            User admin = addNewUser(new User(admin_username, admin_password, admin_email, adminRole));


            // creating the first article (a demo)
//            addNewArticle(new Article("Your First Article!!", "This is the first article!\nSome kind of demo!", admin));
        }

        loadArticles();

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

    public Map<String, User> getUsers () {
        return userRepository.getAllUsers();
    }

    public Map<String, Article> getArticles () {
        return articleRepository.getAllArticles();
    }

    public Map<String, Role> getRoles () { return roleRepository.getAllRoles(); }

    public void printUsers () {
        Map<String, User> users = userRepository.getAllUsers();
        for(Map.Entry<String, User> entry : users.entrySet()) {
            User user = entry.getValue();
            System.out.println(user);
        }
    }

    public void printRoles () {
        Map<String, Role> roles = roleRepository.getAllRoles();
        for (Map.Entry<String,Role> entry : roles.entrySet())
            System.out.println(entry.getValue());
    }

    public void printArticles () {
        Map<String, Article> articles = articleRepository.getAllArticles();
        for(Map.Entry<String, Article> entry : articles.entrySet()) {
            Article article = entry.getValue();
            System.out.println(article);
        }
    }
//
//    public User addNewUser (User newUser) {
//        newUser.setRole(roleRepository.getDefaultRole());
//
//        User addedUser = userRepository.addNewUser(newUser);
//        if(addedUser != null) {
//            BufferedWriter bw = null;
//            try {
//                bw = new BufferedWriter(new FileWriter("pao_project/blog/src/csv/users.csv", true));
//                bw.write(addedUser.toCSV() + "\n");
//                bw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return addedUser;
//    }

    public User addNewUser (User newUser) {
        newUser.setRole(roleRepository.getDefaultRole());

        User addedUser = userRepository.addNewUser(newUser);
        if(addedUser != null) {
            dbService.insertUser(newUser);
        }
        return addedUser;
    }

    public User getUserByNameAndPass (String username, String password) {
        User loggedUser = userRepository.getUserByNameAndPass(username, password);
        if(loggedUser != null) {
           auditService.log(loggedUser.getDisplayName() + " logged in");
        }

        return loggedUser;
    }

//    private Role _addNewRole (Role newRole) {
//        Role addedRole = roleRepository.addNewRole(newRole);
//        if (addedRole == null) return null;
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter("pao_project/blog/src/csv/roles.csv", true));
//            bw.write(addedRole.toCSV() + "\n");
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return addedRole;
//    }

    private Role _addNewRole (Role newRole) {
        Role addedRole = roleRepository.addNewRole(newRole);
        if (addedRole == null) return null;
        dbService.insertRole(newRole);
        return addedRole;
    }

    public Role addNewRole (User loggedUser, Role newRole) {
        if(!loggedUser.isStaff()) return null;
        return this._addNewRole(newRole);
    }

    public Article getArticleById(String articleId) {
        return articleRepository.getArticleById(articleId);
    }

    public Article getArticleByTitle(String artName) {
//        int artId = -1;
//        try {
//            artId = Integer.parseInt(artName);
//        } catch (Exception e) {}
//
//        if(artId != -1) return articleRepository.getArticleById(artId);
        return articleRepository.getArticleByTitle(artName);
    }

//    public Article addNewArticle(Article newArticle) {
//        if(!newArticle.getAuthor().getRole().getPermissions().getPostArticle()) return null;
//        Article returnArticle = articleRepository.addNewArticle(newArticle);
//        System.out.println(returnArticle);
//        if(returnArticle != null) {
//            userRepository.newArticleNotification(returnArticle);
//            auditService.log("A New Article was added");
//            try {
//                BufferedWriter bw = new BufferedWriter(new FileWriter("pao_project/blog/src/csv/articles.csv", true));
//                bw.write(returnArticle.toCSV() + "\n");
//                bw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            auditService.log(returnArticle.getAuthor().getDisplayName() + " added article " + returnArticle.getTitle());
//        }
//        return returnArticle;
//    }

    public Article addNewArticle(Article newArticle) {
        if(!newArticle.getAuthor().getRole().getPermissions().getPostArticle()) return null;
        Article returnArticle = articleRepository.addNewArticle(newArticle);
        System.out.println(returnArticle);
        if(returnArticle != null) {
            userRepository.newArticleNotification(returnArticle);
            dbService.insertArticle(newArticle);
            auditService.log(returnArticle.getAuthor().getDisplayName() + " added article " + returnArticle.getTitle());
        }
        return returnArticle;
    }

    public void save () {
        Map<String, Role> roles = roleRepository.getAllRoles();
        Map<String, User> users = userRepository.getAllUsers();
        Map<String, Article> articles = articleRepository.getAllArticles();

        dbService.saveAll(roles, users, articles);
    }

//    public void save () {
//        Map<String, Role> roles = roleRepository.getAllRoles();
//        Map<String, User> users = userRepository.getAllUsers();
//        Map<String, Article> articles = articleRepository.getAllArticles();
//
//        try {
//            BufferedWriter bw_roles = new BufferedWriter(new FileWriter("pao_project/blog/src/csv/roles.csv"));
//            for(Map.Entry<String, Role> entry : roles.entrySet()) {
//                Role role = entry.getValue();
//                bw_roles.write(role.toCSV() + "\n");
//            }
//            bw_roles.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            BufferedWriter bw_articles = new BufferedWriter(new FileWriter("pao_project/blog/src/csv/articles.csv"));
//            for(Map.Entry<String, Article> entry : articles.entrySet()) {
//                Article article = entry.getValue();
//                bw_articles.write(article.toCSV() + "\n");
//            }
//            bw_articles.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            BufferedWriter bw_users = new BufferedWriter(new FileWriter("pao_project/blog/src/csv/users.csv"));
//            for(Map.Entry<String, User> entry : users.entrySet()) {
//                User user = entry.getValue();
//                bw_users.write(user.toCSV() + "\n");
//            }
//            bw_users.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
