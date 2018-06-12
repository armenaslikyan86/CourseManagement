package am.mainserver.coursemanagement.service.impl;

import am.mainserver.coursemanagement.config.HibernateUtilConfig;
import am.mainserver.coursemanagement.domain.Image;
import am.mainserver.coursemanagement.dto.ImageDto;
import am.mainserver.coursemanagement.repository.ImageRepository;
import am.mainserver.coursemanagement.service.ImageService;
import lombok.val;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


@Service
public class ImageServiceImpl implements ImageService {


    @Autowired
    private SessionFactory sessionFactory;


    @Autowired
    private  ImageRepository imageRepository;



    public  String encodeToString(File file) {

        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedImage bufferedImage;


        try {
            bufferedImage = ImageIO.read(file);
            ImageIO.write(bufferedImage,".jpg", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public  BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    @Transactional
    public void storeImage(final ImageDto imageDto) {
        if (imageDto == null){
            System.out.println("Choose imageUrl");
            //there will be exception
        }else {

            final Image image = new Image();
            image.setImageUrl(imageDto.getImageUrl());
            image.setUser(imageDto.getUser());
            imageRepository.save(image);
        }

    }

    @Override
    public Image getImage(Long userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Image where user_id = :userId");
        query.setParameter("userId", userId);
        Image image = (Image) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return image;
    }

    @Override
    public void deleteImage(Long userId) {
        Image image = getImage(userId);
        imageRepository.delete(image.getId());
    }

}

