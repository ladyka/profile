package by.ladyka.profile.www;

import by.ladyka.profile.dto.PostCommentViewDto;
import by.ladyka.profile.dto.PostViewDto;
import by.ladyka.profile.dto.UserInfoDto;
import by.ladyka.profile.service.FollowService;
import by.ladyka.profile.service.PostCommentService;
import by.ladyka.profile.service.PostService;
import by.ladyka.profile.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UsersService usersService;
    private final FollowService followService;
    private final PostService postService;
    private final PostCommentService postCommentService;

    @GetMapping(value = "/")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("auth", false);
        } else {
            model.addAttribute("auth", true);
            model.addAttribute("dto", usersService.findUser(principal.getName()));
            model.addAttribute("posts", postService.getFeed(principal.getName()));

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
            String avatar,
            String email,
            String fatherName,
            String name,
            String nickname,
            String phone,
            String surname,
            String username
                                     ) {
        UserInfoDto request = new UserInfoDto(avatar,
                email,
                fatherName,
                name,
                nickname,
                phone,
                surname,
                username);
        model.addAttribute("saved", true);
        model.addAttribute("dto", usersService.updateUser(principal.getName(), request));
        return "settings.html";
    }

    @GetMapping(value = "/{nickname}")
    public String publicProfilePage(Model model, Principal principal, @PathVariable String nickname) {
        UserInfoDto profile = usersService.findUserByNickname(nickname);
        model.addAttribute("dto", profile);
        model.addAttribute("myprofile", Objects.equals(principal.getName(), profile.getUsername()));
        model.addAttribute("followers", followService.countFollowers(nickname));
        model.addAttribute("follower", followService.countFollower(nickname));
        return "profile.html";
    }

    @GetMapping(value = "/{nickname}/follow")
    public String followPerson(Model model, Principal principal, @PathVariable String nickname) {
        followService.follow(principal.getName(), nickname);
        return publicProfilePage(model, principal, nickname);
    }

    @GetMapping(value = "/{nickname}/unfollow")
    public String unfollowPerson(Model model, Principal principal, @PathVariable String nickname) {
        followService.unfollow(principal.getName(), nickname);
        return publicProfilePage(model, principal, nickname);
    }

    @GetMapping(value = "/{nickname}/followers")
    public @ResponseBody
    String followers(Model model, Principal principal, @PathVariable String nickname) {
        return String.valueOf(followService.countFollowers(nickname));
    }

    @GetMapping(value = "/{nickname}/follower")
    public @ResponseBody
    String follower(Model model, Principal principal, @PathVariable String nickname) {
        return String.valueOf(followService.countFollower(nickname));
    }

    @GetMapping(value = "/p/create")
    public String create(Model model) {
        PostViewDto post = new PostViewDto();
        post.setDescription("Write here !!!");
        model.addAttribute("post", post);
        return "post.edit.html";
    }

    @PostMapping(value = "/p/save")
    public String savePost(Model model, Principal principal, String id, String description) {
        PostViewDto post = postService.save(id, principal.getName(), description);
        model.addAttribute("post", post);
        return "post.edit.html";
    }

    @PostMapping(value = "/p/c/save")
    public String saveComment(Model model, Principal principal, String postId, String message) {
        postCommentService.save("id", postId, principal.getName(), message);
        return postViewPage(model, principal, postId);
    }

    @GetMapping(value = "/p/{postId}")
    public String postViewPage(Model model, Principal principal, @PathVariable String postId) {
        PostViewDto post = postService.getViewPost(postId, principal.getName());
        model.addAttribute("post", post);
        List<PostCommentViewDto> comments = postCommentService.getComments(postId, principal.getName());
        model.addAttribute("comments", comments);
        return "post.view.html";
    }
}
