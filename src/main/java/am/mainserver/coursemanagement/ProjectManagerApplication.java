package am.mainserver.coursemanagement;

import am.mainserver.coursemanagement.web.MainController;
import am.mainserver.coursemanagement.web.UploadController;
import am.mainserver.coursemanagement.web.UserController;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"am.mainserver.coursemanagement"})
public class ProjectManagerApplication {


    public static void main(String[] args) {

        SpringApplication.run(ProjectManagerApplication.class, args);
    }

}
