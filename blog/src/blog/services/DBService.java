package src.blog.services;

import src.blog.domain.entity.Article;
import src.blog.domain.entity.PollArticle;
import src.blog.domain.entity.Role;
import src.blog.domain.entity.User;

import javax.jws.soap.SOAPBinding;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

public class DBService {
    private static DBService instance = null;
    private final String url;
    private final String user;
    private final String password;

    private DBService (String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public static DBService getInstance(String url, String user, String password) {
        if(instance == null)  {
            instance = new DBService(url, user, password);
        }
        return instance;
    }

    public Connection connectToDB() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    public void insertUser (User newUser) {
        try {
            Connection conn = connectToDB();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Users VALUES(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, newUser.getId());
            preparedStatement.setString(2, newUser.getUsername());
            preparedStatement.setString(3, newUser.getPassword());
            preparedStatement.setString(4, newUser.getRole().getId());
            preparedStatement.setString(5, newUser.getDisplayName());
            preparedStatement.setString(6, newUser.getEmail());
            preparedStatement.setString(7, newUser.getStringRegisterDate());

            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertRole (Role newRole) {
        try {
            Connection conn = connectToDB();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Roles VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, newRole.getId());
            preparedStatement.setString(2, newRole.getTitle());
            preparedStatement.setBoolean(3, newRole.isDefaultRole());
            preparedStatement.setBoolean(4, newRole.isStaff());
            preparedStatement.setBoolean(5, newRole.getPermissions().getChangeName());
            preparedStatement.setBoolean(6, newRole.getPermissions().getSeeArticle());
            preparedStatement.setBoolean(7, newRole.getPermissions().getPostArticle());
            preparedStatement.setBoolean(8, newRole.getPermissions().getEditArticle());

            preparedStatement.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertArticle (Article newArticle) {
        try {
            Connection conn = connectToDB();
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Articles VALUES(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, newArticle.getId());
            preparedStatement.setString(2, newArticle.getTitle());
            preparedStatement.setString(3, newArticle.getContent());
            preparedStatement.setString(4, newArticle.getAuthor().getId());
            preparedStatement.setString(5, newArticle.getStringPostDate());

            if(newArticle instanceof PollArticle) {
                preparedStatement.setInt(7, 1);
                preparedStatement.setString(6, ((PollArticle) newArticle).optionsToCSV());
            } else {
                preparedStatement.setInt(7, 0);
                preparedStatement.setString(6, "");
            }

            preparedStatement.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAll (Map<String, Role> roles, Map<String, User> users, Map<String, Article> articles) {
        try {
            Connection conn = connectToDB();
            for(Map.Entry<String, Role> entry : roles.entrySet()) {
                Role role = entry.getValue();

                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Roles SET id = ?, title = ?, defaultRole = ?, staff = ?, can_changeName = ?, can_seeArticle = ?, can_postArticle = ?, can_editArticle = ? WHERE id = ?");

                preparedStatement.setString(1, role.getId());
                preparedStatement.setString(2, role.getTitle());
                preparedStatement.setBoolean(3, role.isDefaultRole());
                preparedStatement.setBoolean(4, role.isStaff());
                preparedStatement.setBoolean(5, role.getPermissions().getChangeName());
                preparedStatement.setBoolean(6, role.getPermissions().getSeeArticle());
                preparedStatement.setBoolean(7, role.getPermissions().getPostArticle());
                preparedStatement.setBoolean(8, role.getPermissions().getEditArticle());
                preparedStatement.setString(9, role.getId());

                int n = preparedStatement.executeUpdate();

                if(n == 0) {
                    insertRole(role);
                }
            }

            for(Map.Entry<String, User> entry : users.entrySet()) {
                User user = entry.getValue();

                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Users SET id = ?, username = ?, password = ?, roleId = ?, displayName = ?, email = ?, registerDate = ? WHERE id = ?");
                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getRole().getId());
                preparedStatement.setString(5, user.getDisplayName());
                preparedStatement.setString(6, user.getEmail());
                preparedStatement.setString(7, user.getStringRegisterDate());

                preparedStatement.setString(8, user.getId());

                int n = preparedStatement.executeUpdate();

                if(n == 0) {
                    insertUser(user);
                }
            }

            for(Map.Entry<String, Article> entry : articles.entrySet()) {
                Article article = entry.getValue();
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Articles SET title = ?, content = ?, authorId = ?, postDate = ?, optionsCSV = ?, type = ? WHERE id = ?");
                preparedStatement.setString(1, article.getTitle());
                preparedStatement.setString(2, article.getContent());
                preparedStatement.setString(3, article.getAuthor().getId());
                preparedStatement.setString(4, article.getStringPostDate());

                if(article instanceof PollArticle) {
                    preparedStatement.setInt(6, 1);
                    preparedStatement.setString(5, ((PollArticle) article).optionsToCSV());
                } else {
                    preparedStatement.setInt(6, 0);
                    preparedStatement.setString(5, "");
                }
                preparedStatement.setString(7, article.getId());

                int n = preparedStatement.executeUpdate();
                if(n == 0) {
                    insertArticle(article);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
