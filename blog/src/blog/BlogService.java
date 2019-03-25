package blog;

import java.util.ArrayList;

public class BlogService {
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Article> articles = new ArrayList<Article>();
    private ArrayList<Role> roles = new ArrayList<Role>();

    private static BlogService instance;

    private BlogService (String admin_username, String admin_password, String admin_email) {

        // creating default roles (Administrator and Member)
        roles.add(new Role("Administrator", new PermissionsBuilder().setAll().build(), false, true));
        roles.add(new Role("Member", new PermissionsBuilder().canSeeArticle().build(), true, false));

        // creating the first account (admin of the platform)
        users.add(new User(admin_username, admin_password, admin_email, roles.get(0)));

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
        return this.users;
    }


}
