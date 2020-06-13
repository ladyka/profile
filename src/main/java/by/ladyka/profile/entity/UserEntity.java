package by.ladyka.profile.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends BasicEntity implements UserDetails {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String fatherName;
    private String avatar;
    private LocalDate birthday;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private String regToken;

    @OneToMany(mappedBy = "user")
    private List<AuthorityEntity> authorities = new ArrayList<>();
}
