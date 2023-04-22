package jobs.scaler.scaler_flipkart_assignment.repositories;

import jobs.scaler.scaler_flipkart_assignment.models.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

}
