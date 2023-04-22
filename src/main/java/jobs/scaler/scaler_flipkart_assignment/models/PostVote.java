package jobs.scaler.scaler_flipkart_assignment.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "post_vote")
public class PostVote {

//    @Id
//    @JoinColumn(name = "user_id")
//    @ManyToOne
//    private User user;
//
//    @Id
//    @JoinColumn(name = "post_id")
//    @ManyToOne
//    private Post post;

    @EmbeddedId
    private PostVotePrimaryKey postVotePrimaryKey;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "vote_type")
    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}
