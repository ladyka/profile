package by.ladyka.profile.service;

import by.ladyka.profile.dto.PostCommentViewDto;
import by.ladyka.profile.entity.PostCommentEntity;
import by.ladyka.profile.entity.UserEntity;
import by.ladyka.profile.repository.PostCommentRepository;
import by.ladyka.profile.repository.UserEntityRepository;
import by.ladyka.profile.service.mapper.PostCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;
    private final PostCommentMapper postCommentMapper;
    private final UserEntityRepository userEntityRepository;

    public List<PostCommentViewDto> getComments(String postId, String username) {
        UserEntity viewer = userEntityRepository.findByUsername(username).orElseThrow();
        return postCommentRepository.findAllByPostIdOrderByCreatedDateDesc(postId)
                .stream().map(commentEntity -> {
                    String authorComment = userEntityRepository.findById(Long.valueOf(commentEntity.getOwnerId()))
                            .map(UserEntity::getNickname).orElseThrow();
                    return postCommentMapper.toDto(commentEntity, authorComment, viewer.getZoneId());
                }).collect(Collectors.toList());
    }

    public void save(String postCommentId, String postId, String username, String message) {
        UserEntity creator = userEntityRepository.findByUsername(username).orElseThrow();
        PostCommentEntity comment = (postCommentId != null)
                                    ? postCommentRepository.findById(postCommentId)
                                            .orElseGet(() -> createPostCommentEntity(postId, creator))
                                    : createPostCommentEntity(postId, creator);
        if (!Objects.equals(comment.getOwnerId(), String.valueOf(creator.getId())) || !Objects.equals(comment.getPostId(), postId)) {
            throw new AccessDeniedException("Няма доступу!!");
        }
        comment.setMessage(message);
        postCommentRepository.save(comment);
    }

    private PostCommentEntity createPostCommentEntity(String postId, UserEntity creator) {
        PostCommentEntity newComment = new PostCommentEntity();
        newComment.setPostId(postId);
        newComment.setOwnerId(String.valueOf(creator.getId()));
        return newComment;
    }
}
