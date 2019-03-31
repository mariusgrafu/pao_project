package src.blog.domain.entity;

import java.util.ArrayList;
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

    private ArrayList<Notification> notifications = new ArrayList<Notification>();

    public User(String username, String password, String displayName, String email, Role role, Date registerDate, Preferences preferences) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.email = email;
        this.role = role;
        this.registerDate = registerDate;
        this.preferences = preferences;
    }

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

        this.displayName = username;
        this.preferences = new Preferences();
    }

    public User(String username, String password, String displayName, String email) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.email = email;

        this.preferences = new Preferences();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public int getUnseenNotificationsCount () {
        int count = 0;
        for(Notification notification : notifications) {
            if(!notification.isSeen()) count++;
        }

        return count;
    }

    public String getHelloMessage () {
        int unseenNotifications = getUnseenNotificationsCount();
        return "Hello " + displayName + "! " +
                unseenNotifications + " Notification" + ((unseenNotifications != 1)?("s"):(""));
    }

    public void printNotifications () {
        for(Notification notification : notifications) {
            System.out.println(notification);
            notification.setSeen(true);
        }
    }

    public boolean isStaff () {
        return role.isStaff();
    }

    @Override
    public String toString() {
        return "" +
                "[" + id + "] " + displayName + ' ' +
                "(" + role.getTitle() + ") ";
    }

    public String debugString() {
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

    public void addNotification (Notification newNotification) {
        notifications.add((newNotification));
    }
}
