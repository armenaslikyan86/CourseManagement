package am.mainserver.coursemanagement.service;

import am.mainserver.coursemanagement.domain.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> getAnnouncements();
}
