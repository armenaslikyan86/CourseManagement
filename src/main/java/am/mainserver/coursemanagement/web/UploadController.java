package am.mainserver.coursemanagement.web;

import am.mainserver.coursemanagement.dto.ImageDto;
import am.mainserver.coursemanagement.service.ImageService;
import am.mainserver.coursemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
public class UploadController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    private static String UPLOADED_FOLDER = System.getProperty("user.dir") + File.separator + "img/";

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                           Principal principal, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            return "profile_index.html";
        }

        try {
            byte[] bytes = file.getBytes();
            String filepath = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename()).toString();
            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(file.getBytes());

            if (imageService.getImage(userService.getUserId(principal.getName())) != null) {
                imageService.deleteImage(userService.getUserId(principal.getName()));
            }
            ImageDto imageDto = new ImageDto();
            imageDto.setImageUrl(UPLOADED_FOLDER + file.getOriginalFilename());
            imageDto.setUser(userService.getByEmail(principal.getName()));
            imageService.storeImage(imageDto);

            redirectAttributes.addFlashAttribute("file_name", file.getOriginalFilename());

            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/profile/";
    }
}
