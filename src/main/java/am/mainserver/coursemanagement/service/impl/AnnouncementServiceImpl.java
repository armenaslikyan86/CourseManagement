package am.mainserver.coursemanagement.service.impl;

import am.mainserver.coursemanagement.domain.Announcement;
import am.mainserver.coursemanagement.repository.AnnouncementRepository;
import am.mainserver.coursemanagement.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    AnnouncementRepository announcementRepository;

    @Override
    public List<Announcement> getAnnouncements() {
        List<Announcement> list = new ArrayList<>();
        for (Announcement announcement: announcementRepository.findAll()
             ) {
            list.add(announcement);
        }
        return list;
    }
}
