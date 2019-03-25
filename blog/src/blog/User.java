package blog;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String password;

    private Role role;

    private String displayName;
    private String email;

    private Date registerDate;

    private Preferences preferences;

    static private int count;

    public User(String username, String password, String displayName, String email, Role role, Date registerDate, Preferences preferences) {
        this.id = count++;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
        this.registerDate = registerDate;
        this.preferences = preferences;
    }

    public User(String username, String password, String email, Role role) {
        this.id = count++;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

        this.displayName = username;
        this.registerDate = new Date();
        this.preferences = new Preferences();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", registerDate=" + registerDate +
                ", preferences=" + preferences +
                '}';
    }
}
