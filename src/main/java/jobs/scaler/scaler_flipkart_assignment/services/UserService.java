package jobs.scaler.scaler_flipkart_assignment.services;

import jobs.scaler.scaler_flipkart_assignment.repositories.*;
import jobs.scaler.scaler_flipkart_assignment.shellcontroller.Commands;
import jobs.scaler.scaler_flipkart_assignment.models.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentVoteRepository commentVoteRepository;
    private final PostVoteRepository postVoteRepository;
    private final UserRepository userRepository;
    private Long postId, commentId;

    private static final Set<String> sortingOrder = new HashSet<>(Arrays.asList("followed_users", "vote_score", "comment_score","timestamp"));

    public UserService(PostRepository postRepository,
                       CommentRepository commentRepository,
                       CommentVoteRepository commentVoteRepository,
                       PostVoteRepository postVoteRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.commentVoteRepository = commentVoteRepository;
        this.postVoteRepository = postVoteRepository;
        this.userRepository = userRepository;

        postId = postRepository.findLastIndex();
        commentId = commentRepository.findLastIndex();

        postId = postId == null ?  0L : ++postId;
        commentId = commentId == null ?  0L : ++commentId;
    }

    public String createPost(String postText) {

        Post post = new Post();
        post.setId(postId);
        post.setCreatedOn(LocalDateTime.now());
        post.setUser(Commands.getUser());
        post.setPost_text(postText);
        post.setComment_count(0L);
        post.setVote_count(0L);
        postRepository.save(post);
        postId++;
        return "post created successfully";
    }

    public String commentOnPost(long post_id, String comment_text) {

        Optional<Post> post = postRepository.findById(post_id);

        if(post.isEmpty()) {
            return "post does not exist";
        }

        else {
            Comment comment = new Comment();
            comment.setCreatedOn(LocalDateTime.now());
            comment.setId(commentId);
            comment.setComment_text(comment_text);
            comment.setUser(Commands.getUser());
            comment.setPost(post.get());
            comment.setParent_comment(null);
            comment.setVote_count(0L);
            postRepository.updateCommentCount(post.get().getId());
            commentRepository.save(comment);
            commentId++;

            return "comment successfully posted";
        }
    }

    public String upVotePost(long post_id) {

        Optional<Post> post = postRepository.findById(post_id);
        if(post.isEmpty()) {
            return "post does not exist";
        }
        else {
            PostVote postVote = initializePostVote(post.get());
            postVote.setVoteType(VoteType.UP_VOTE);
            postVoteRepository.save(postVote);
            postRepository.increasePostVoteCount(post.get().getId());
            return "post successfully up voted";
        }

    }

    public String downVotePost(long post_id) {

        Optional<Post> post = postRepository.findById(post_id);
        if(post.isEmpty()) {
            return "post does not exist";
        }
        else {
            PostVote postVote = initializePostVote(post.get());
            postVote.setVoteType(VoteType.DOWN_VOTE);
            postVoteRepository.save(postVote);
            postRepository.decreasePostVoteCount(post.get().getId());
            return "post successfully down voted";
        }
    }

    public void showCommentsOnPost (long id) {

        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) {
            System.out.println("post does not exits");
        } else {
            List<Comment> commentList = commentRepository.getAllCommentsOnPost(id);

            for (Comment comment : commentList) {
                System.out.println("Comment Id: " +comment.getId());
                System.out.println("Comment text: " +comment.getComment_text());
                System.out.println("Commented by: " +comment.getUser().getUsername());
                System.out.println("Votes: " +comment.getVote_count());
                System.out.println("Timestamp: " +getTimeAgo(comment.getCreatedOn()));
                System.out.println();
            }
        }

    }

    public String replyOnComment(long comment_id, String reply_text) {

        Optional<Comment> parent_comment = commentRepository.findById(comment_id);
        if(parent_comment.isEmpty()) {
            return "no such comment with comment_id "+comment_id+" exists";
        }
        else {
            Comment reply  = new Comment();
            reply.setCreatedOn(LocalDateTime.now());
            reply.setId(commentId);
            reply.setUser(Commands.getUser());
            reply.setParent_comment(parent_comment.get());
            reply.setComment_text(reply_text);
            reply.setVote_count(0L);
            commentRepository.save(reply);
            Post post = parent_comment.get().getPost();
            postRepository.updateCommentCount(post.getId());
            commentId++;

            return "your reply successfully posted against comment id "+comment_id;
        }
    }

    public String upVoteComment(long comment_id) {

        Optional<Comment> comment = commentRepository.findById(comment_id);
        if(comment.isEmpty()) {
            return "comment does not exist";
        }
        else {
            CommentVote commentVote = initializeCommentVote(comment.get());
            commentVote.setVoteType(VoteType.UP_VOTE);
            commentVoteRepository.save(commentVote);
            commentRepository.increaseCommentVoteCount (comment.get().getId());
            return "comment successfully up voted";
        }
    }

    public String downVoteComment(long comment_id) {

        Optional<Comment> comment = commentRepository.findById(comment_id);
        if(comment.isEmpty()) {
            return "comment does not exist";
        }
        else {
            CommentVote commentVote = initializeCommentVote(comment.get());
            commentVote.setVoteType(VoteType.DOWN_VOTE);
            commentVoteRepository.save(commentVote);
            commentRepository.decreaseCommentVoteCount (comment.get().getId());

            return "comment down voted successfully";
        }
    }

    public String showRepliesOnComment(long id) {

        Optional<Comment> parent_comment = commentRepository.findById(id);
        if(parent_comment.isPresent()) {
            return "no such comment exist";
        }
        else {

            List<Comment> commentList = commentRepository.getReplies (id);

            for(Comment comment : commentList) {
                System.out.println("Reply by : " + comment.getId());
                System.out.println("replied by : " + comment.getUser().getUsername());
                System.out.println("Text : " + comment.getComment_text());
                System.out.println("Score : " + comment.getVote_count());
                System.out.println("Timestamp : " + getTimeAgo(comment.getCreatedOn()));
                System.out.println();
            }
        }
        return "";
    }

    public String followUser(String username) {

        User loggedInUser = Commands.getUser();
        Optional<User> follow_user = userRepository.findByUsername(username);

        if (follow_user.isEmpty()) {
            return "no such user exist";
        }

        else if (loggedInUser.getId() == follow_user.get().getId()) {
            return "Invalid input.";
        }

        else {
            userRepository.followUser(loggedInUser.getId(), follow_user.get().getId());
            return "successfully followed user "+follow_user.get().getUsername();
        }
    }


    public void showNewsFeed(String sortBy) {

        User loggedInUser = Commands.getUser();

        List<Long> followedUsersId = userRepository.getFollowingUsersId (loggedInUser.getId());

        List<Post> newsFeed = switch (sortBy) {
            case "vote_score" -> postRepository.getAllPostSortedByVoteCount(followedUsersId);
            case "comment_score" -> postRepository.getAllPostSortedByCommentCount(followedUsersId);
            case "timestamp" -> postRepository.getAllPostSortedByTimeStamp(followedUsersId);
            default -> postRepository.getAllPostSortedByFollowedUser(followedUsersId);
        };

        for (Post post : newsFeed) {
            System.out.println("Post Id " + post.getId());
            System.out.println("Posted by: " + post.getUser().getUsername());
            System.out.println("Text: " + post.getPost_text());
            System.out.println("Score: " + post.getVote_count());
            System.out.println("Comments: " + post.getComment_count());
            System.out.println("Timestamp: " + getTimeAgo(post.getCreatedOn()));
            System.out.println();
        }

    }

    private static String getTimeAgo(LocalDateTime timestamp) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(timestamp, now);
        if (duration.toDays() > 0) {
            return duration.toDays() + "d ago";
        } else if (duration.toHours() > 0) {
            return duration.toHours() + "h ago";
        } else if (duration.toMinutes() > 0) {
            return duration.toMinutes() + "m ago";
        } else {
            return "just now";
        }
    }

    private PostVote initializePostVote (Post post) {
        PostVote postVote = new PostVote();
        postVote.setCreatedOn(LocalDateTime.now());
        PostVotePrimaryKey postVotePrimaryKey= new PostVotePrimaryKey();
        postVotePrimaryKey.setUser(Commands.getUser());
        postVotePrimaryKey.setPost(post);
        postVote.setPostVotePrimaryKey(postVotePrimaryKey);
        return postVote;
    }

    private CommentVote initializeCommentVote (Comment comment) {
        CommentVote commentVote = new CommentVote();
        commentVote.setCreatedOn(LocalDateTime.now());
        CommentVotePrimaryKey commentVotePrimaryKey = new CommentVotePrimaryKey();
        commentVotePrimaryKey.setComment(comment);
        commentVotePrimaryKey.setUser(Commands.getUser());
        commentVote.setCommentVotePrimaryKey(commentVotePrimaryKey);
        return commentVote;
    }
}
