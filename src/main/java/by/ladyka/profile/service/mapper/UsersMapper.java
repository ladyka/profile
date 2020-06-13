package by.ladyka.profile.service.mapper;

import by.ladyka.profile.dto.UserInfoDto;
import by.ladyka.profile.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UserInfoDto toInfoDto(UserEntity userEntity);
}
