package by.ladyka.profile.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "post_comment")
@EntityListeners(AuditingEntityListener.class)
public class PostCommentEntity extends JpaEntity {
    private String postId;
    private String message;
    private String ownerId;
}
