package project.news_feed_cli.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Embeddable
public class PostVotePrimaryKey implements Serializable {

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @Column(name = "vote_type")
    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}
