package by.ladyka.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private String avatar;
    private String email;
    private String fatherName;
    private String name;
    private String nickname;
    private String phone;
    private String surname;
    private String username;
}
