package by.ladyka.profile.www;

import by.ladyka.profile.config.ZoneIds;
import by.ladyka.profile.dto.JoinDto;
import by.ladyka.profile.exceptions.ApplicationException;
import by.ladyka.profile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String join(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("dto", new JoinDto());
            model.addAttribute("zoneIds", ZoneIds.zoneIds);
            return "join.html";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String joinAction(Model model, Principal principal, JoinDto joinDto) {
        if (principal == null) {
            try {
                userService.join(joinDto);
                model.addAttribute("title", "Спасибо за регистрацию");
                model.addAttribute("description", "На ваш электронный адрес " + joinDto.getEmail() + " выслано письмо с подтверждением!");
                return "message.html";
            } catch (ApplicationException ex) {
                model.addAttribute("dto", joinDto);
                model.addAttribute("message", ex.getMessage());
                ex.printStackTrace();
                return "join.html";
            }
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirm(Model model,Principal principal, String email, String token) {
        try {
            if (principal != null) {
                userService.banDuplicateRegistration(principal.getName(), email);
            } else {
                userService.confirm(email, token);
            }
            model.addAttribute("title", "Спасибо за регистрацию");
            model.addAttribute("description", "Учетная запись подверждена. Можете войти на сайт!");
            return "message.html";
        } catch (ApplicationException ex) {
            model.addAttribute("title", "Подтверждение регистрации");
            model.addAttribute("description", ex.getMessage());
            ex.printStackTrace();
            return "message.html";
        }
    }
}
