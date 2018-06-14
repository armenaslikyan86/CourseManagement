package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.common.RoleType;
import am.mainserver.coursemanagement.domain.Course;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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


        List<String> enrolled_courses = new ArrayList<>();
        userService.getByEmail(principal.getName()).getCourses().forEach(course -> {
            enrolled_courses.add(course.getName());
        });

        model.addAttribute("enrolled_courses", Arrays.toString(enrolled_courses.toArray()));


        model.addAttribute("user", userService.getByEmail(principal.getName()));
        model.addAttribute(
                "message", "You are logged in as " + userService.getUserFullName(principal.getName()));
        model.addAttribute("userID", "with user ID " + userService.getUserId(principal.getName()));
        model.addAttribute("announcements", announcementService.getAnnouncements());
        model.addAttribute("courses", courseService.getCourses());

        model.addAttribute("enrolled_courses", userService.getByEmail(principal.getName()).getCourses());

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

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(User user, @RequestParam("id") Long id) {
        user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt(12)));
        userService.update(id, user);
        return "redirect:/profile/";
    }

    @PostMapping(value = "/enroll")
    public String enroll(@RequestParam("id") Long id, Principal principal) {
       User user = userService.getByEmail(principal.getName());
       user.getCourses().add(courseService.getCourseById(id));
       userService.update(user.getId(), user);
       return "redirect:/profile/";
    }

}
