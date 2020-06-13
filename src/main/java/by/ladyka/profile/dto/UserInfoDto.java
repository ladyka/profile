package by.ladyka.profile.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserInfoDto {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String fatherName;
    private String avatar;
    private LocalDate birthday;
}
