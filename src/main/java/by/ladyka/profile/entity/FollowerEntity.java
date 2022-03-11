package by.ladyka.profile.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "followers")
@EntityListeners(AuditingEntityListener.class)
@IdClass(FollowerEntity.FollowerRecordId.class)
public class FollowerEntity {
    @Id
    @Column
    private String userId;

    @Id
    @Column
    private String followerId;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class FollowerRecordId implements Serializable {
        private String userId;
        private String followerId;
    }
}
