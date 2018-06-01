package am.mainserver.coursemanagement.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainRestController {

    @GetMapping(value = "/")
    public String hello() {
        return "index";
    }
}
