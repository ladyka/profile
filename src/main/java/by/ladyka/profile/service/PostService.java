package by.ladyka.profile.service;

import by.ladyka.profile.dto.PostViewDto;
import by.ladyka.profile.entity.PostEntity;
import by.ladyka.profile.entity.UserEntity;
import by.ladyka.profile.repository.PostRepository;
import by.ladyka.profile.repository.UserEntityRepository;
import by.ladyka.profile.service.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserEntityRepository userEntityRepository;
    private final FollowService followService;

    public PostViewDto getViewPost(String postId, String usernameViewer) {
        PostEntity post = postRepository.findById(postId).orElseThrow();
        UserEntity viewer = userEntityRepository.findByUsername(usernameViewer).orElseThrow();
        UserEntity owner = userEntityRepository.findById(Long.valueOf(post.getOwnerId())).orElseThrow();
        boolean access = followService.isFollower(String.valueOf(owner.getId()), String.valueOf(viewer.getId()));
        if (access) {
            return postMapper.toDto(post, owner.getNickname(), viewer.getZoneId());
        } else {
            throw new AccessDeniedException("Няма доступа!");
        }

    }

    public PostViewDto save(String postId, String username, String description) {
        UserEntity creator = userEntityRepository.findByUsername(username).orElseThrow();
        PostEntity blankPost = new PostEntity();
        blankPost.setOwnerId(String.valueOf(creator.getId()));
        PostEntity post = (postId != null)
                          ? postRepository.findById(postId).orElse(blankPost)
                          : blankPost;
        if (!Objects.equals(post.getOwnerId(), String.valueOf(creator.getId()))) {
            throw new AccessDeniedException("Няма доступу!!");
        }
        post.setDescription(description);
        postRepository.save(post);
        return postMapper.toDto(post, creator.getNickname(), creator.getZoneId());
    }

    public List<PostViewDto> getFeed(String username) {
        UserEntity viewer = userEntityRepository.findByUsername(username).orElseThrow();
        return postRepository.findFeedByViewerId(String.valueOf(viewer.getId()))
                .stream().map(post -> {
                    UserEntity owner = userEntityRepository.findById(Long.valueOf(post.getOwnerId())).orElseThrow();
                    return postMapper.toDto(post, owner.getNickname(), viewer.getZoneId());
                })
                .collect(Collectors.toList());
    }
}
