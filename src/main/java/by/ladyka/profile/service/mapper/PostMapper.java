package by.ladyka.profile.service.mapper;

import by.ladyka.profile.dto.PostViewDto;
import by.ladyka.profile.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createDateTime", expression = "java(post.getCreatedDate().atZone(java.time.ZoneId.of(viewerTimeZoneId)))")
    PostViewDto toDto(PostEntity post, String author, String viewerTimeZoneId);

}
