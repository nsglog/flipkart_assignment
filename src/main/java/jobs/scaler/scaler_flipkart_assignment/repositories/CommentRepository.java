package jobs.scaler.scaler_flipkart_assignment.repositories;
import jobs.scaler.scaler_flipkart_assignment.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select max(id) from user", nativeQuery = true)
    Long findLastIndex();

    @Query(value = "select * from comment where parent_comment_id = ?1", nativeQuery = true)
    List<Comment> getReplies(long id);

    @Query(value = "select * from comment where post_id = ?1 and parent_comment_id = null order by created_on" ,nativeQuery = true)
    List<Comment> getAllCommentsOnPost(long id);
}
