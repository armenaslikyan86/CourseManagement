package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.domain.Course;
import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.service.CourseService;
import am.mainserver.coursemanagement.service.UserService;
import am.mainserver.coursemanagement.service.exception.CourseExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @GetMapping("/course")
    public String getCourse(@RequestParam("id") Long id, Model model) {

        Course course = courseService.getCourseById(id);
        String courseName = course.getName().toLowerCase();

        model.addAttribute("course", course);

        if (courseName.startsWith("java") && courseName.charAt(4) != 's') {
            courseName = "java.jpg";
            model.addAttribute("course_img_name", courseName);
        } else if (courseName.contains("c#") || courseName.contains("c sharp")) {
            courseName = "c sharp.jpg";
            model.addAttribute("course_img_name", courseName);
        } else if (courseName.contains("javascript")) {
            courseName = "javascript.jpg";
            model.addAttribute("course_img_name", courseName);
        } else if (courseName.contains("ios")) {
            courseName = "ios.jpg";
            model.addAttribute("course_img_name", courseName);
        } else {
            model.addAttribute("course_img_name", "default.jpg");
        }

        return "course";
    }
 }
