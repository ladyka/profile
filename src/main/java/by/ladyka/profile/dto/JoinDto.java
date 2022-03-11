package by.ladyka.profile.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JoinDto {
    private String username = "";
    private String password = "";
    private String passwordCheck = "";
    private String name = "";
    private String surname = "";
    private String email = "";
    private String phone = "";
    private String fatherName = "";
    private String avatar = "";
    private String nickname = UUID.randomUUID().toString();
}
