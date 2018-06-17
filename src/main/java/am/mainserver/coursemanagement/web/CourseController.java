package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.common.RoleType;
import am.mainserver.coursemanagement.domain.Course;
import am.mainserver.coursemanagement.domain.User;
import am.mainserver.coursemanagement.dto.CourseDto;
import am.mainserver.coursemanagement.service.CourseService;
import am.mainserver.coursemanagement.service.UserService;
import am.mainserver.coursemanagement.service.exception.CourseExistException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;


    @PostMapping("/create_course")
    public String createCourse(Principal principal, @Validated final CourseDto courseDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws CourseExistException {
        courseDto.getUsers().add(userService.convertToUserDto(userService.getByEmail(principal.getName())));
        courseDto.setTutorName(userService.getUserFullName(principal.getName()));
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
    public String getCourse(@RequestParam("id") Long id, Model model, Principal principal) {

        Course course = courseService.getCourseById(id);
        String courseName = course.getName().toLowerCase();

        model.addAttribute("course", course);

        if (principal == null) {
            model.addAttribute("course_register", "true");
        }

        if (principal != null && userService.getByEmail(principal.getName()).getRoleType().equals(RoleType.TUTOR)) {

            model.addAttribute("tutor_register", "true");
            userService.getByEmail(principal.getName()).getCourses().forEach(course1 -> {
                if (course1.getId() == id) {
                    model.addAttribute("editable", "true");
                    model.addAttribute("tutor_edit", "ok");
                }
            });

        }

        if (courseName.equals("java") || ( courseName.contains("java") && courseName.length()>=4 && courseName.charAt(4) != 's')) {
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


        model.addAttribute("tutor", courseService.getCourseById(id).getTutorName());

        return "course";
    }

    @RequestMapping(value = "/updateCourse", method = RequestMethod.POST)
    public String updateCourse(Course course, @RequestParam("id") Long id, Principal principal) {
        course.setTutorName(userService.getUserFullName(principal.getName()));
        courseService.update(course, id);
        return "redirect:/profile/";
    }

    @GetMapping(value = "/students")
    public String getStudents(@RequestParam("id") Long id, Principal principal, Model model) {

        List<User> students = new ArrayList<>();

        courseService.getCourseById(id).getUsers().forEach(user -> {
            if (user.getId() != userService.getByEmail(principal.getName()).getId()) {
                students.add(user);
            }
        });
        model.addAttribute("students", students);
        return "students";
    }
 }
