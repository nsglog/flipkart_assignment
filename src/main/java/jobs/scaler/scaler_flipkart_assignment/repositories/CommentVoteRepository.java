package jobs.scaler.scaler_flipkart_assignment.repositories;

import jobs.scaler.scaler_flipkart_assignment.models.CommentVote;
import jobs.scaler.scaler_flipkart_assignment.models.CommentVotePrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, CommentVotePrimaryKey> {

}
