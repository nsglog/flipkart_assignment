package project.news_feed_cli.shellcontroller;

import project.news_feed_cli.models.User;
import project.news_feed_cli.repositories.UserRepository;
import project.news_feed_cli.services.UserService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class Commands {

    private final UserRepository userRepository;
    private final UserService userService;
    private static User user;
    private boolean canCommentAndVotePost;
    private boolean canReplyAndVoteComment;
    private Long lastUserId;

    public static User getUser () {
        return user;
    }

    public Commands(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        lastUserId = userRepository.findLastIndex();
        lastUserId = lastUserId == null ?  0L : ++lastUserId;
    }

    @ShellMethod(value = "login --u 'username' --p 'password' ")
    @ShellMethodAvailability("userNotLoggedIn")
    public String login (String u, String p) {
        User loginUser = userRepository.findByUsernameAndPassword(u, p);
        if(loginUser != null) {
            user = loginUser;
            return "Welcome "+u;
        }
        else {
            return "Invalid credentials";
        }
    }

    @ShellMethod(value = "signup --u 'username' --e 'email' --p 'password'")
    @ShellMethodAvailability("userNotLoggedIn")
    public String signup (String u, String e, String p) {

        User newUser = new User();
        newUser.setId(lastUserId);
        newUser.setUsername(u);
        newUser.setEmail(e);
        newUser.setPassword(p);
        userRepository.save(newUser);
        lastUserId++;
        return "user "+u+" successfully signed up . please log in to continue.";
    }

    @ShellMethod(value = "create-post --text 'post_content' ")
    @ShellMethodAvailability("userLoggedIn")
    public String createPost (String text) {
        canReplyAndVoteComment = false;
        canCommentAndVotePost = false;
        return userService.createPost(text);
    }

    @ShellMethod(value = "show-news-feed --sortOrder 'sort order'    //sort orders :'followed_users', 'vote_score', 'comment_score', 'timestamp'")
    @ShellMethodAvailability("userLoggedIn")
    public void showNewsFeed (String sortOrder) {
        userService.showNewsFeed(sortOrder);
        canCommentAndVotePost = true;
        canReplyAndVoteComment = false;
    }

    @ShellMethod(value = "follow-user --u 'username'")
    @ShellMethodAvailability("userLoggedIn")
    public String followUser (String u) {
        canCommentAndVotePost = false;
        canReplyAndVoteComment = false;
        return userService.followUser(u);
    }


    @ShellMethod(value = "logout")
    @ShellMethodAvailability("userLoggedIn")
    public String logout () {
        user = null;
        canReplyAndVoteComment = false;
        canCommentAndVotePost = false;
        return "user successfully logged out";
    }

    @ShellMethod(value = "comment-on-post --id 'post_id' --text 'comment_text'")
    @ShellMethodAvailability("canUserCommentAndVoteOnPost")
    public String commentOnPost (long id, String text) {
        return userService.commentOnPost(id, text);
    }

    @ShellMethod(value = "up-vote-post --id 'post_id'")
    @ShellMethodAvailability("canUserCommentAndVoteOnPost")
    public String upVotePost (long id) {
        return userService.upVotePost(id);
    }

    @ShellMethod(value = "down-vote-post --id 'post_id'")
    @ShellMethodAvailability("canUserCommentAndVoteOnPost")
    public String downVotePost (long id) {
        return userService.downVotePost(id);
    }

    @ShellMethod(value = "show-comments-on-post --id 'postId'")
    @ShellMethodAvailability(value = "canUserCommentAndVoteOnPost")
    public void showCommentsOnPost (long id) {
        userService.showCommentsOnPost(id);
        canReplyAndVoteComment = true;
        canCommentAndVotePost = false;
    }

    @ShellMethod(value = "up-vote-comment --id 'comment_id'")
    @ShellMethodAvailability("canUserReplyAndVoteOnComment")
    public String upVoteComment (long id) {
        return userService.upVoteComment(id);
    }

    @ShellMethod(value = "down-vote-comment --id 'comment_id'")
    @ShellMethodAvailability("canUserReplyAndVoteOnComment")
    public String downVoteComment (long id) {
        return userService.downVoteComment(id);
    }

    @ShellMethod(value = "show-replies-on-comment --id 'commentId'")
    @ShellMethodAvailability("canUserReplyAndVoteOnComment")
    public String showRepliesOnComment (long id) {
        return userService.showRepliesOnComment (id);
    }

    @ShellMethod(value = "reply-on-comment --id 'comment_id' --text 'reply_text'")
    @ShellMethodAvailability("canUserReplyAndVoteOnComment")
    public String replyOnComment (long id, String text) {
        return userService.replyOnComment(id, text);
    }

    private Availability userNotLoggedIn () {
        String s = "user already logged in";
        return user == null ? Availability.available():Availability.unavailable(s);
    }

    private Availability userLoggedIn () {
        String s = "You must be logged in to use this command";
        return user == null ? Availability.unavailable(s) : Availability.available();
    }

    private Availability canUserCommentAndVoteOnPost () {
        String s = "You can only comment after viewing the news feed";
        return canCommentAndVotePost ? Availability.available() : Availability.unavailable(s);
    }

    private Availability canUserReplyAndVoteOnComment () {
        String s = "You can only reply to the comment after viewing the comments on post";
        return canReplyAndVoteComment ? Availability.available() : Availability.unavailable(s);
    }
}
