package by.ladyka.profile.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostCommentViewDto {
    private String id;
    private String message;
    private String author;
    private ZonedDateTime createDateTime;
}
