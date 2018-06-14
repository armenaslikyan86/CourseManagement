package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.service.AnnouncementService;
import am.mainserver.coursemanagement.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;


@Controller
public class MainController {


    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private CourseService courseService;

    @GetMapping(value = "/")
    public String  index(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/profile/";
        }
        model.addAttribute("announcements", announcementService.getAnnouncements());
        model.addAttribute("courses", courseService.getCourses());
        return "index";
    }



    @GetMapping(value = {
            "/contact",
            "/profile/contact"
    })
    public String  contact() {
        return "contact";
    }


    @GetMapping(value = {
            "/about",
            "/profile/about"
    })
    public String  about() {
        return "about";
    }


}


