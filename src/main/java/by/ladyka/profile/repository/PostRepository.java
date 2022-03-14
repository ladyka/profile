package by.ladyka.profile.repository;

import by.ladyka.profile.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, String> {
    @Query(value = "select p.* from post p where owner_id in (select f.user_id from followers f where f.follower_id = :viewerId) or owner_id = :viewerId order by created_date desc", nativeQuery = true)
    List<PostEntity> findFeedByViewerId(@Param("viewerId") String viewerId);
}
