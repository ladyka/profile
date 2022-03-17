package by.ladyka.profile.service.mapper;

import by.ladyka.profile.dto.JoinDto;
import by.ladyka.profile.dto.UserInfoDto;
import by.ladyka.profile.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UserInfoDto toInfoDto(UserEntity userEntity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "birthday", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "enabled", constant = "false")
    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "regToken", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(JoinDto joinDto);

    void updateEntity(@MappingTarget UserEntity userEntity, UserInfoDto dto);
}
