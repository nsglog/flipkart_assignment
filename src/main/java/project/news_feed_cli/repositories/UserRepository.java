package project.news_feed_cli.repositories;

import jakarta.transaction.Transactional;
import project.news_feed_cli.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    @Query(value = "select max(id) from user", nativeQuery = true)
    Long findLastIndex();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into user_following values (?1, ?2)", nativeQuery = true)
    void followUser(Long userId, Long following_userId);

    @Query(value = "select following_user_id from user_following where user_id = ?1",nativeQuery = true)
    List<Long> getFollowingUsersId(long id);

    Optional<User> findByUsername(String username);
}
