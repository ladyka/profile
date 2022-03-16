package by.ladyka.profile.service;

import by.ladyka.profile.entity.ReactionEntity;
import by.ladyka.profile.entity.UserEntity;
import by.ladyka.profile.enums.Reaction;
import by.ladyka.profile.repository.ReactionsRepository;
import by.ladyka.profile.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionsService {
    private final ReactionsRepository reactionsRepository;
    private final UserEntityRepository userEntityRepository;

    public long countLikes(String postId) {
        return reactionsRepository.countAllByReactionAndPostId(Reaction.LIKE, postId);
    }

    public long countDislikes(String postId) {
        return reactionsRepository.countAllByReactionAndPostId(Reaction.DISLIKE, postId);
    }

    public void makeReaction(String username, String postId, Reaction reaction) {
        UserEntity reactionPerson = userEntityRepository.findByUsername(username).orElseThrow();
        ReactionEntity entity = reactionsRepository.findByPostIdAndOwnerId(postId,
                String.valueOf(reactionPerson.getId())).orElseGet(() -> {
            ReactionEntity reactionEntity = new ReactionEntity();
            reactionEntity.setPostId(postId);
            reactionEntity.setOwnerId(String.valueOf(reactionPerson.getId()));
            reactionEntity.setReaction(reaction);
            return reactionEntity;
        });
        entity.setReaction(reaction);
        reactionsRepository.save(entity);
    }
}
