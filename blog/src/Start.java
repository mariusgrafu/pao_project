import src.blog.domain.entity.*;
import src.blog.services.BlogService;

import java.util.Scanner;

public class Start {

    public static void main(String[] args) {

        System.out.println("This aims to be a blog 🙈");

        BlogService blogService = BlogService.getInstance("Marius", "asd", "marius@email.com");

        //////

        boolean run = true;
        User loggedUser = null;
        Scanner scanner = new Scanner(System.in);

        while(run) {
            System.out.println("\n------------------------\n");

            if(loggedUser == null) {
                System.out.print("Create New User | ");
                System.out.print("Log in | ");
            } else {
                System.out.println(loggedUser.getHelloMessage());
                System.out.print("Log out | ");
                System.out.print("Notifications | ");
                System.out.print("Preferences | ");

                if(loggedUser.getRole().getPermissions().getChangeName())
                    System.out.print("Change Display Name | ");

                if(loggedUser.getRole().getPermissions().getSeeArticle())
                    System.out.print("Articles | ");


                if(loggedUser.isStaff()) {
                    System.out.print("Create New Role | ");
                }
            }

            System.out.print("Members | ");
            System.out.print("Roles | ");

            System.out.println("Exit");
            String option = scanner.nextLine();

            option = option.toLowerCase();
            if(loggedUser == null &&
                    !option.equals("log in") && !option.equals("create new user") &&
                    !option.equals("members") && !option.equals("roles") &&
                    !option.equals("exit")) {
                continue;

            }

            switch (option) {
                case "0": // quick exit
                case "exit": // Exit
                    blogService.save();
                    run = false;
                    break;
                case "create new user": // Create New User
                    while(loggedUser == null) {
                        String username, password, displayName, email;
                        System.out.println("username: ");
                        username = scanner.nextLine();
                        System.out.println("display name: ");
                        displayName = scanner.nextLine();
                        System.out.println("password: ");
                        password = scanner.nextLine();
                        System.out.println("email address: ");
                        email = scanner.nextLine();
                        loggedUser = blogService.addNewUser(new User(username, password, displayName, email));
                    }
                    break;
                case "log in":
                    while(loggedUser == null) {
                        String username, password;
                        System.out.println("username: ");
                        username = scanner.nextLine();
                        System.out.println("password: ");
                        password = scanner.nextLine();
                        loggedUser = blogService.getUserByNameAndPass(username, password);
                    }
                    break;
                case "members":
                    blogService.printUsers();
                    break;
                case "roles":
                    blogService.printRoles();
                    break;
                case "log out":
                    loggedUser = null;
                    break;
                case "notifications":
                    boolean notificationRun = true;
                    while(notificationRun) {
                        System.out.println("Back");

                        loggedUser.printNotifications();

                        String notificationOption = scanner.nextLine();
                        if(notificationOption.toLowerCase().equals("back")) notificationRun = false;
                    }
                    break;
                case "create new role":
                    Role newRole = null;
                    while(newRole == null) {
                        String title, canChangeName, canSeeArticle, canPostArticle, canEditArticle;
                        System.out.println("role title: ");
                        title = scanner.nextLine();
                        System.out.println("can they change their display name? ");
                        canChangeName = scanner.nextLine();
                        System.out.println("can they see articles? ");
                        canSeeArticle = scanner.nextLine();
                        System.out.println("can they post articles? ");
                        canPostArticle = scanner.nextLine();
                        System.out.println("can they edit articles? ");
                        canEditArticle = scanner.nextLine();

                        newRole = blogService.addNewRole(loggedUser, new Role(title,
                                new Permissions(
                                        getBool(canChangeName),
                                        getBool(canSeeArticle),
                                        getBool(canPostArticle),
                                        getBool(canEditArticle)
                                )
                                ));

                    }
                    break;
                case "change display name":
                    if(loggedUser.getRole().getPermissions().getChangeName()){
                        String newDisplayName;
                        System.out.println("new display name: ");
                        newDisplayName = scanner.nextLine();
                        loggedUser.setDisplayName(newDisplayName);
                    }
                    break;
                case "preferences":
                    boolean compactView = loggedUser.getPreferences().hasCompactView();
                    System.out.println("compact view? (currently " + compactView + ")");
                    compactView = getBool(scanner.nextLine());
                    loggedUser.getPreferences().setCompactView(compactView);
                    break;
                case "articles":
                    if(!loggedUser.getRole().getPermissions().getSeeArticle()) break;
                    boolean articlesRun = true;

                    while(articlesRun) {
                        System.out.print("Select Article | ");
                        if(loggedUser.getRole().getPermissions().getPostArticle())
                            System.out.print("Write Article | ");
                        if(loggedUser.getRole().getPermissions().getPostArticle())
                            System.out.print("Make Poll | ");
                        System.out.println("Back");

                        blogService.printArticles();

                        String articlesOption = scanner.nextLine();

                        switch(articlesOption.toLowerCase()) {
                            case "back":
                                articlesRun = false;
                                break;
                            case "write article":
                                if(!loggedUser.getRole().getPermissions().getPostArticle()) break;
                                Article newArticle = null;
                                while(newArticle == null) {
                                    String title, content;
                                    System.out.println("title: ");
                                    title = scanner.nextLine();
                                    System.out.println("content: ");
                                    content = scanner.nextLine();
                                    newArticle = blogService.addNewArticle(new Article(title, content, loggedUser));
                                }
                                break;
                            case "make poll":
                                if(!loggedUser.getRole().getPermissions().getPostArticle()) break;
                                PollArticle newPollArticle = null;
                                while(newPollArticle == null) {
                                    String title, content;
                                    System.out.println("title: ");
                                    title = scanner.nextLine();
                                    System.out.println("content: ");
                                    content = scanner.nextLine();
                                    newPollArticle = (PollArticle)blogService.addNewArticle(new PollArticle(title, content, loggedUser));
                                }
                                boolean pollArticleOptionsRun = true;

                                while(pollArticleOptionsRun || newPollArticle.getOptions().size() == 0) {
                                    System.out.print("Add option");
                                    if(newPollArticle.getOptions().size() != 0) System.out.print(" | Back");
                                    System.out.println();
                                    String pollArticleOption = scanner.nextLine();
                                    switch(pollArticleOption.toLowerCase()) {
                                        case "back":
                                            if(newPollArticle.getOptions().size() != 0) pollArticleOptionsRun = false;
                                            break;
                                        case "add option":
                                            PollOption newPollOption = null;
                                            while(newPollOption == null) {
                                                String title;
                                                System.out.println("option title: ");
                                                title = scanner.nextLine();
                                                newPollOption = newPollArticle.addNewOption(new PollOption(title));
                                            }
                                            break;
                                    }
                                }
                                break;
                            case "select article":
                                System.out.println("article title: ");
                                String artName = scanner.nextLine();

                                Article selectedArticle = blogService.getArticleByTitle(artName);

                                if(selectedArticle != null) {
                                    boolean articleRun = true;

                                    while(articleRun) {
                                        System.out.print("Comment | ");
                                        if (selectedArticle instanceof PollArticle) {
                                            System.out.print("Vote | ");
                                        }
                                        if(loggedUser.getRole().getPermissions().getEditArticle())
                                            System.out.print("Edit | ");
                                        System.out.println("Back");

                                        selectedArticle.printArticle(loggedUser.getPreferences().hasCompactView());

                                        String articleOption = scanner.nextLine();
                                        switch(articleOption.toLowerCase()) {
                                            case "back":
                                                articleRun = false;
                                                break;
                                            case "vote":
                                                if (!(selectedArticle instanceof PollArticle)) break;
                                                PollOption votedOption = null;
                                                while(votedOption == null) {
                                                    System.out.println("Back");
                                                    System.out.println("option id: ");
                                                    String optionIdStr = scanner.nextLine();
                                                    if(optionIdStr.toLowerCase().equals("back")) break;
                                                    try {
                                                        int optionId =Integer.parseInt(optionIdStr);
                                                        votedOption = ((PollArticle) selectedArticle).voteOptionById(optionId - 1, loggedUser);
                                                    } catch (Exception e) {}
                                                }
                                                break;
                                            case "comment":
                                                Comment newComment = null;
                                                while(newComment == null) {
                                                    String commentContent;
                                                    System.out.println("comment: ");
                                                    commentContent = scanner.nextLine();
                                                    newComment = selectedArticle.addNewComment(new Comment(commentContent, loggedUser));
                                                }
                                                break;
                                            case "edit":
                                                if(!loggedUser.getRole().getPermissions().getEditArticle()) break;
                                                String title, content;
                                                System.out.println("edit title? ");
                                                title = scanner.nextLine();
                                                if(getBool(title)){
                                                    System.out.println("title: ");
                                                    title = scanner.nextLine();
                                                    selectedArticle.setTitle(title);
                                                }
                                                System.out.println("edit content? ");
                                                content = scanner.nextLine();
                                                if(getBool(content)) {
                                                    System.out.println("content: ");
                                                    content = scanner.nextLine();
                                                    selectedArticle.setContent(content);
                                                }
                                                break;
                                        }
                                    }
                                }

                                break;
                        }
                    }

                    break;
            }
        }

    }

    static private boolean getBool(String answer) {
        return answer.equals("yes");
    }
}
