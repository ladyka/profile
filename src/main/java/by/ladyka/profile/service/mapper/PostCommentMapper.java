package by.ladyka.profile.service.mapper;

import by.ladyka.profile.dto.PostCommentViewDto;
import by.ladyka.profile.entity.PostCommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostCommentMapper {
    @Mapping(target = "createDateTime", expression = "java(commentEntity.getCreatedDate().atZone(java.time.ZoneId.of(viewerTimeZoneId)))")
    PostCommentViewDto toDto(PostCommentEntity commentEntity, String author, String viewerTimeZoneId);

}
