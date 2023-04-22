package jobs.scaler.scaler_flipkart_assignment.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "comment_vote")
public class CommentVote {
//    @JoinColumn(name = "user_id")
//    @ManyToOne
//    private User user;
//
//    @JoinColumn(name = "comment_id")
//    @ManyToOne
//    private Comment comment;

    @EmbeddedId
    private CommentVotePrimaryKey commentVotePrimaryKey;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "vote_type")
    @Enumerated(EnumType.STRING)
    private VoteType voteType;
}
