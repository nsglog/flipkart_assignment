package project.news_feed_cli.repositories;

import jakarta.transaction.Transactional;
import project.news_feed_cli.models.PostVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVote, Long> {

    @Query(value = "select * from post_vote where user_id = ?2 and post_id = ?1",nativeQuery = true)
    PostVote findByUserAndPost(long id, long id1);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update post_vote set vote_type = 'UP_VOTE' where user_id = ?1 and post_id = ?2", nativeQuery = true)
    void updateVoteTypeToUpVote(long id, long postId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update post_vote set vote_type = 'DOWN_VOTE' where user_id = ?1 and post_id = ?2", nativeQuery = true)
    void updateVoteTypeToDownVote(long id, long postId);
}
