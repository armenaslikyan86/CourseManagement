package am.mainserver.coursemanagement.repository;

import am.mainserver.coursemanagement.domain.Announcement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement, Long> {

}
