package jobs.scaler.scaler_flipkart_assignment.repositories;

import jobs.scaler.scaler_flipkart_assignment.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "select max(id) from user", nativeQuery = true)
    Long findLastIndex();
}
