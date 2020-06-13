package by.ladyka.profile.www;

import by.ladyka.profile.dto.UserInfoDto;
import by.ladyka.profile.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UsersService usersService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String chats(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("auth", false);
        } else {
            model.addAttribute("auth", true);
            model.addAttribute("dto", usersService.findUser(principal.getName()));
        }
        return "index.html";
    }


}
