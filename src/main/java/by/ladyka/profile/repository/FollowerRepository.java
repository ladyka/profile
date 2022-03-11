package by.ladyka.profile.repository;

import by.ladyka.profile.entity.FollowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<FollowerEntity, FollowerEntity.FollowerRecordId> {
    long countByFollowerId(String followerId);

    long countByUserId(String userId);
}
