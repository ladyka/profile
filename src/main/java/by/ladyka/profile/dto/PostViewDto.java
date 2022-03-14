package by.ladyka.profile.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class PostViewDto {
    private String id;
    private String description;
    private String author;
    private ZonedDateTime createDateTime;
}
