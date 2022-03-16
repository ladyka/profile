package by.ladyka.profile.repository;

import by.ladyka.profile.entity.PostCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostCommentEntity, String> {
    List<PostCommentEntity> findAllByPostIdOrderByCreatedDateDesc(String postId);
}
