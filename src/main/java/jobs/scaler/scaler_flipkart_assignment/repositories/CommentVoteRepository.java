package jobs.scaler.scaler_flipkart_assignment.repositories;

import jakarta.transaction.Transactional;
import jobs.scaler.scaler_flipkart_assignment.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, CommentVotePrimaryKey> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into comment_vote values (?1, ?2, ?3, ?4)", nativeQuery = true)
    void insertValues(Long user_id, Long comment_id, LocalDateTime createdOn, VoteType voteType);

    @Query(value = "select * from comment_vote where user_id = ?2 and comment_id = ?1",nativeQuery = true)
    CommentVote findByUserAndComment(Long comment_id, Long user_id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update table comment_vote set vote_type = 'UP_VOTE' where user_id = ?1 and comment_id = ?2", nativeQuery = true)
    void updateVoteTypeToUpVote(long id, long commentId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update table comment_vote set vote_type = 'DOWN_VOTE' where user_id = ?1 and comment_id = ?2", nativeQuery = true)
    void updateVoteTypeToDownVote(long id, long commentId);
}
