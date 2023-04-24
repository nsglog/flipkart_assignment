package project.news_feed_cli.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "comment_text")
    private String comment_text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn (name = "parent_comment_id")
    private Comment parent_comment;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "vote_count")
    private Long vote_count;
}
