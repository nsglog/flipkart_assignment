package jobs.scaler.scaler_flipkart_assignment.repositories;

import jakarta.transaction.Transactional;
import jobs.scaler.scaler_flipkart_assignment.models.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    @Query(value = "select * from comment_vote where user_id = ?2 and post_id = ?1",nativeQuery = true)
    PostVote findByUserAndPost(long id, long id1);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update table post_vote set vote_type = NULL where user_id = ?1 and post_id = ?2", nativeQuery = true)
    void updateVoteType(long id, long postId);
}
