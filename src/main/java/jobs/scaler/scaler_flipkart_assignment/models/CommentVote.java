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

    @EmbeddedId
    private CommentVotePrimaryKey commentVotePrimaryKey;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
