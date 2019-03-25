import blog.BlogService;
import blog.User;

import java.util.ArrayList;

public class Start {

    public static void main(String[] args) {

        System.out.println("This aims to be a blog");

        BlogService blogService = BlogService.getInstance("marius", "parolaluimarius", "marius@email.com");

        // testing users

        ArrayList<User> users = blogService.getUsers();

        for(User user : users) {
            System.out.println(user.toString());
        }

    }
}
