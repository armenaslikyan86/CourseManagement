package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class MainRestController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public String  index() {
        return "index";
    }


    @GetMapping(value = "/contact")
    public String  contact() {
        return "contact";
    }


    @GetMapping(value = "/about")
    public String  about() {
        return "about";
    }


}


