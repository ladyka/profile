package by.ladyka.profile.service;

import by.ladyka.profile.entity.FollowerEntity;
import by.ladyka.profile.entity.UserEntity;
import by.ladyka.profile.repository.FollowerRepository;
import by.ladyka.profile.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowerRepository followerRepository;
    private final UserEntityRepository userEntityRepository;

    public long countFollowers(String nickname) {
        UserEntity userEntity = userEntityRepository.findByNickname(nickname).orElseThrow();
        return followerRepository.countByFollowerId(String.valueOf(userEntity.getId()));
    }

    public long countFollower(String nickname) {
        UserEntity userEntity = userEntityRepository.findByNickname(nickname).orElseThrow();
        return followerRepository.countByUserId(String.valueOf(userEntity.getId()));
    }

    public void follow(String username, String nickname) {
        UserEntity me = userEntityRepository.findByUsername(username).orElseThrow();
        UserEntity myStar = userEntityRepository.findByNickname(nickname).orElseThrow();
        FollowerEntity.FollowerRecordId followRecordId = new FollowerEntity.FollowerRecordId();
        followRecordId.setUserId(String.valueOf(me.getId()));
        followRecordId.setFollowerId(String.valueOf(myStar.getId()));
        if (followerRepository.findById(followRecordId).isEmpty()) {
            FollowerEntity followerEntity = new FollowerEntity();
            followerEntity.setUserId(followRecordId.getUserId());
            followerEntity.setFollowerId(followRecordId.getFollowerId());
            followerRepository.save(followerEntity);
        }
    }

    public void unfollow(String username, String nickname) {
        UserEntity me = userEntityRepository.findByUsername(username).orElseThrow();
        UserEntity myStar = userEntityRepository.findByNickname(nickname).orElseThrow();
        FollowerEntity.FollowerRecordId followRecordId = new FollowerEntity.FollowerRecordId();
        followRecordId.setUserId(String.valueOf(me.getId()));
        followRecordId.setFollowerId(String.valueOf(myStar.getId()));
        Optional<FollowerEntity> oFollowRecord = followerRepository.findById(followRecordId);
        oFollowRecord.ifPresent(followerRepository::delete);
    }

    public boolean isFollower(String ownerId, String viewerId) {
        return Objects.equals(ownerId, viewerId) || followerRepository.existsById(new FollowerEntity.FollowerRecordId(ownerId, viewerId));
    }
}
