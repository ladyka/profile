package by.ladyka.profile.entity;

import by.ladyka.profile.enums.Reaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "reactions")
@EntityListeners(AuditingEntityListener.class)
public class ReactionEntity extends JpaEntity {
    private String postId;
    @Enumerated(EnumType.STRING)
    private Reaction reaction;
    private String ownerId;
}
