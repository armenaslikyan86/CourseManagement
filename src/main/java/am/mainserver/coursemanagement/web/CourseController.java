package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.dto.UserCreationRequestDto;
import am.mainserver.coursemanagement.dto.UserDto;
import am.mainserver.coursemanagement.service.CourseService;
import am.mainserver.coursemanagement.service.UserService;
import am.mainserver.coursemanagement.service.exception.CourseExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @PostMapping("/create_course")
    public String createCourse(Principal principal, @Validated final CourseDto courseDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws CourseExistException {
        courseDto.getUsers().add(userService.convertToUserDto(userService.getByEmail(principal.getName())));
        User updatedUser = userService.getByEmail(principal.getName());
        updatedUser.getCourses().add(courseService.convertToCourse(courseDto));
        userService.update(userService.getUserId(principal.getName()), updatedUser);
        if (bindingResult.hasErrors()) {
            return "redirect:/profile/";
        }
        redirectAttributes.addFlashAttribute("action_successfull", "OK");
        return "redirect:/profile/";
    }
}
