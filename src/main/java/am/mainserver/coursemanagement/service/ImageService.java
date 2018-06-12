package am.mainserver.coursemanagement.service;

import am.mainserver.coursemanagement.domain.Image;
import am.mainserver.coursemanagement.dto.ImageDto;
import java.awt.image.BufferedImage;
import java.io.File;


public interface ImageService {
    String encodeToString(File file);
    BufferedImage decodeToImage(String imageString);
    void storeImage(ImageDto image);
    Image getImage(Long userId);
    void deleteImage(Long userId);
}