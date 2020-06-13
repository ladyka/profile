package by.ladyka.profile.service;

import by.ladyka.profile.dto.UserInfoDto;
import by.ladyka.profile.entity.UserEntity;
import by.ladyka.profile.repository.UserEntityRepository;
import by.ladyka.profile.service.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UserEntityRepository userEntityRepository;
    private final UsersMapper usersMapper;

    public UserInfoDto findUser(String username) {
        UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow();
        return usersMapper.toInfoDto(userEntity);
    }
}
