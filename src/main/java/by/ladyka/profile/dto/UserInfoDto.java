package by.ladyka.profile.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserInfoDto {
    private LocalDate birthday;
    private String avatar;
    private String email;
    private String fatherName;
    private String name;
    private String nickname;
    private String phone;
    private String surname;
    private String username;
}
