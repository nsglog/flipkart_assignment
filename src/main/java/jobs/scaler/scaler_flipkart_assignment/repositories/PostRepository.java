package jobs.scaler.scaler_flipkart_assignment.repositories;
import jakarta.transaction.Transactional;
import jobs.scaler.scaler_flipkart_assignment.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select max(id) from post", nativeQuery = true)
    Long findLastIndex();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update post set comment_count = comment_count + 1 where id = ?1", nativeQuery = true)
    void updateCommentCount(long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update post set vote_count = vote_count + 1 where id = ?1", nativeQuery = true)
    void increasePostVoteCount(long id);

    @Query(value = "select * from post where user_id in (:id)",nativeQuery = true)
    List<Post> getAllPostSortedByFollowedUser(@Param(value = "id") List<Long> followedUsersId);


    @Query(value = "select * from post where user_id in (:id) order by vote_count desc ",nativeQuery = true)
    List<Post> getAllPostSortedByVoteCount(@Param(value = "id") List<Long> followedUsersId);

    @Query(value = "select * from post where user_id in (:id) order by comment_count desc ",nativeQuery = true)
    List<Post> getAllPostSortedByCommentCount(@Param(value = "id") List<Long> followedUsersId);

    @Query(value = "select * from post where user_id in (:id) order by created_on desc",nativeQuery = true)
    List<Post> getAllPostSortedByTimeStamp(@Param(value = "id") List<Long> followedUsersId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update post set vote_count = vote_count - 1 where id = ?1", nativeQuery = true)
    void decreasePostVoteCount(long id);
}
