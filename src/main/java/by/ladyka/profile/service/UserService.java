package by.ladyka.profile.service;

import by.ladyka.profile.config.LadykaRole;
import by.ladyka.profile.dto.JoinDto;
import by.ladyka.profile.entity.AuthorityEntity;
import by.ladyka.profile.entity.UserEntity;
import by.ladyka.profile.exceptions.InvalidRegTokenException;
import by.ladyka.profile.exceptions.PasswordMismatchException;
import by.ladyka.profile.exceptions.UserExistException;
import by.ladyka.profile.repository.AuthorityRepository;
import by.ladyka.profile.repository.UserEntityRepository;
import by.ladyka.profile.service.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserEntityRepository userEntityRepository;
    private final AuthorityRepository authorityRepository;
    private final UsersMapper usersMapper;
    private final EmailService emailService;

    public void join(JoinDto joinDto) {
        if (!Objects.equals(joinDto.getPassword(), joinDto.getPasswordCheck())) {
            throw new PasswordMismatchException();
        }
        userEntityRepository.findByUsername(joinDto.getUsername()).ifPresent(userEntity -> {
            throw new UserExistException(UserExistException.FIELD.USERNAME);
        });
        userEntityRepository.findByEmail(joinDto.getUsername()).ifPresent(userEntity -> {
            throw new UserExistException(UserExistException.FIELD.EMAIL);
        });

        UserEntity user = usersMapper.toEntity(joinDto);
        userEntityRepository.save(user);
        authorityRepository.save(new AuthorityEntity(LadykaRole.USER.name(), user));
        emailService.sendJoinEmail(user.getEmail(), user.getRegToken());
    }

    public void confirm(String email, String token) {
        UserEntity user = userEntityRepository.findByEmail(email).orElseThrow();
        if (Objects.equals(user.getRegToken(), token)) {
            user.setEnabled(true);
            user.setRegToken(UUID.randomUUID().toString() + System.currentTimeMillis());
            userEntityRepository.save(user);
        } else {
            throw new InvalidRegTokenException();
        }
    }

    public void banDuplicateRegistration(String username, String email) {
        Optional<UserEntity> byUsername = userEntityRepository.findByUsername(username);
        Optional<UserEntity> byEmail = userEntityRepository.findByEmail(email);
        if (byEmail.isPresent() && byUsername.isPresent()) {
            UserEntity userEntityByUsername = byUsername.get();
            UserEntity userEntityByEmail = byEmail.get();
            if(!Objects.equals(userEntityByEmail.getId(), userEntityByUsername.getId())) {
                userEntityByEmail.setAccountNonLocked(false);
                userEntityByEmail.setEnabled(false);
                userEntityRepository.save(userEntityByEmail);
                userEntityByUsername.setAccountNonLocked(false);
                userEntityByUsername.setEnabled(false);
                userEntityRepository.save(userEntityByUsername);
            }
        }
    }
}
