package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.common.RoleType;
import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.dto.UserCreationRequestDto;
import am.mainserver.coursemanagement.dto.UserDto;
import am.mainserver.coursemanagement.service.AnnouncementService;
import am.mainserver.coursemanagement.service.CourseService;
import am.mainserver.coursemanagement.service.UserService;
import am.mainserver.coursemanagement.service.exception.EmailExistException;
import am.mainserver.coursemanagement.service.impl.ImageServiceImpl;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new UserCreationRequestDto());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid final UserCreationRequestDto creationRequest) throws EmailExistException {
        userService.register(creationRequest);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/get")
    public UserDto getUser(@Valid @NotBlank @RequestParam("email") final String email) {
        final User user = userService.getByEmail(email);
        final UserDto userDto = userService.convertToUserDto(user);
        return userDto;
    }


    @GetMapping(value = "/profile/")
    public String  profile_index(Model model, Principal principal) {


        model.addAttribute("user", userService.getByEmail(principal.getName()));
        model.addAttribute(
                "message", "You are logged in as " + userService.getUserFullName(principal.getName()));
        model.addAttribute("userID", "with user ID " + userService.getUserId(principal.getName()));
        model.addAttribute("announcements", announcementService.getAnnouncements());
        model.addAttribute("courses", courseService.getCourses());


        if (imageService.getImage(userService.getUserId(principal.getName())) != null) {
            String[] tokens = imageService.getImage(userService.getUserId(principal.getName())).getImageUrl().split("/");
            String file_name = tokens[6];
            model.addAttribute("file_name", file_name);
        }

        if (userService.getByEmail(principal.getName()).getRoleType().equals(RoleType.TUTOR)) {
            model.addAttribute("create_course", "CREATE COURSE");
        }

        model.addAttribute("course", new CourseDto());

        return "profile_index";

    }

}
