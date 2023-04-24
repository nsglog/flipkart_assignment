package project.news_feed_cli.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "post_text")
    private String post_text;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "comment_count")
    private Long comment_count;

    @Column(name = "vote_count")
    private Long vote_count;
}
