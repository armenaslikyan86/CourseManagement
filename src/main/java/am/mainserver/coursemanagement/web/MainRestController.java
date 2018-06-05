package am.mainserver.coursemanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainRestController {

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


