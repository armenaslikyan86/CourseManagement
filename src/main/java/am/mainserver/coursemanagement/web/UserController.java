package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.dto.UserCreationRequestDto;
import am.mainserver.coursemanagement.dto.UserDto;
import am.mainserver.coursemanagement.service.UserService;
import am.mainserver.coursemanagement.service.impl.EmailExistException;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


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
        final UserDto userDto = convertToUserDto(user);
        return userDto;
    }

    @RequestMapping(value = "/profile" ,method = RequestMethod.GET)
    public String profile(Model model, Principal principal){
        if(principal == null) {
            return "redirect:/login";
        }
        model.addAttribute(
                "message", "You are logged in as " + userService.getUserFullName(principal.getName()));
        model.addAttribute("userID", "with user ID" + userService.getUserId(principal.getName()));
        return "profile";
    }

    private UserDto convertToUserDto(final User user) {
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRoleType(user.getRoleType());
        final Set<CourseDto> courseDtoList = user.getCourses().stream()
                .map(course -> {
                    final CourseDto courseDto = new CourseDto();
                    courseDto.setId(course.getId());
                    courseDto.setName(course.getName());
                    courseDto.setDescription(course.getDescription());
                    courseDto.setDuration(course.getDuration());
                    courseDto.setPrice(course.getPrice());
                    return courseDto;
                }).collect(Collectors.toSet());
        userDto.setCourses(courseDtoList);
        return userDto;
    }


}
