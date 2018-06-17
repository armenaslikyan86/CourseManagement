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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new UserCreationRequestDto());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid final UserCreationRequestDto creationRequest, HttpServletRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws EmailExistException {

        //Email Validation

        if (!emailValidation(creationRequest.getEmail())) {
            redirectAttributes.addFlashAttribute("email_validate", "ok");
            return "redirect:/addUser";
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/addUser";
        }


        userService.register(creationRequest);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(creationRequest.getEmail(), creationRequest.getPasswordHash());
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        try {
            Authentication auth = authenticationManager.authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(auth);}
            catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/profile/";
    }

    private boolean emailValidation(String email) {

        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
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


    @GetMapping(value = {"/profile/", "/profile"})
    public String  profile_index(Model model, Principal principal) {

        model.addAttribute("user", userService.getByEmail(principal.getName()));
        model.addAttribute(
                "message", "You are logged in as " + userService.getUserFullName(principal.getName()));
        model.addAttribute("userID", "with user ID " + userService.getUserId(principal.getName()));
        model.addAttribute("announcements", announcementService.getAnnouncements());
        model.addAttribute("courses", courseService.getCourses());

        model.addAttribute("enrolled_courses", userService.getByEmail(principal.getName()).getCourses());

        String file_name;
        if (imageService.getImage(userService.getUserId(principal.getName())) != null) {
            String[] tokens = imageService.getImage(userService.getUserId(principal.getName())).getImageUrl().split("/");
            file_name = tokens[6];
            model.addAttribute("file_name", file_name);
        } else {
            file_name = "noimage.jpg";
            model.addAttribute("file_name", file_name);
        }


        if (userService.getByEmail(principal.getName()).getRoleType().equals(RoleType.TUTOR)) {
            model.addAttribute("create_course", "CREATE COURSE");
            model.addAttribute("students", userService.getByEmail(principal.getName()).getCourses());
        }

        model.addAttribute("course", new CourseDto());

        return "profile_index";

    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(User user, @RequestParam("id") Long id, Principal principal) {
        user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt(12)));
        user.setCourses(userService.getByEmail(principal.getName()).getCourses());
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
