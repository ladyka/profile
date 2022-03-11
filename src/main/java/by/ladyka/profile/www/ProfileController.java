package by.ladyka.profile.www;

import by.ladyka.profile.dto.UserInfoDto;
import by.ladyka.profile.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UsersService usersService;

    @GetMapping(value = "/")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("auth", false);
        } else {
            model.addAttribute("auth", true);
            model.addAttribute("dto", usersService.findUser(principal.getName()));
        }
        return "index.html";
    }

    @GetMapping(value = "/settings")
    public String settingsProfile(Model model, Principal principal) {
        model.addAttribute("dto", usersService.findUser(principal.getName()));
        return "settings.html";
    }

    @PostMapping(value = "/settings")
    public String settingsEditProfile(Model model, Principal principal,
            LocalDate birthday,
            String avatar,
            String email,
            String fatherName,
            String name,
            String nickname,
            String phone,
            String surname,
            String username
                                     ) {
        UserInfoDto request = new UserInfoDto(birthday,
                avatar,
                email,
                fatherName,
                name,
                nickname,
                phone,
                surname,
                username);
        model.addAttribute("dto", usersService.updateUser(principal.getName(), request));
        return "settings.html";
    }

    @GetMapping(value = "/{nickname}")
    public String publicProfilePage(Model model, Principal principal, @PathVariable String nickname) {
        model.addAttribute("dto", usersService.findUserByNickname(nickname));
        return "profile.html";
    }
}
