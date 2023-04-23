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

    @EmbeddedId
    private PostVotePrimaryKey postVotePrimaryKey;

    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
