package by.ladyka.profile.repository;

import by.ladyka.profile.entity.ReactionEntity;
import by.ladyka.profile.enums.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionsRepository extends JpaRepository<ReactionEntity, String> {
    long countAllByReactionAndPostId(Reaction reaction, String postId);

    Optional<ReactionEntity> findByPostIdAndOwnerId(String postId, String ownerId);
}
