package by.ladyka.profile.repository;

import by.ladyka.profile.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByNickname(String username);

    UserEntity findByUsernameOrEmail(String username, String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByFatherName(String fatherName);

}
