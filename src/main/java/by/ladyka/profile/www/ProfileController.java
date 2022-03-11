package by.ladyka.profile.www;

import by.ladyka.profile.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
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

    @GetMapping(value = "/nickname")
    public String publicProfilePage(Model model, Principal principal, @PathVariable String nickname) {
        model.addAttribute("dto", usersService.findUserByNickname(nickname));
        return "profile.html";
    }
}
