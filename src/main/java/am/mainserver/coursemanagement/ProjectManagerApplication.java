package am.mainserver.coursemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ImportAutoConfiguration(classes = {JerseyConfig.class, PersistenceConfig.class})
@ComponentScan(basePackages = {"am.mainserver.coursemanagement"})
public class ProjectManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProjectManagerApplication.class, args);
    }
}
