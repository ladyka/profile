package by.ladyka.profile.www;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String chats(Model model, Principal principal) {
        return "index.html";
    }


}
