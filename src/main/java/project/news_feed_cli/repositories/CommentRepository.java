package project.news_feed_cli.repositories;
import jakarta.transaction.Transactional;
import project.news_feed_cli.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select max(id) from comment", nativeQuery = true)
    Long findLastIndex();

    @Query(value = "select * from comment where parent_comment_id = ?1", nativeQuery = true)
    List<Comment> getReplies(long id);

    @Query(value = "select * from comment where post_id = ?1 and parent_comment_id is null order by created_on" ,nativeQuery = true)
    List<Comment> getAllCommentsOnPost(long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update comment set vote_count = vote_count + 1 where id = ?1", nativeQuery = true)
    void increaseCommentVoteCount(long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update comment set vote_count = vote_count - 1 where id = ?1", nativeQuery = true)
    void decreaseCommentVoteCount(long id);
}
