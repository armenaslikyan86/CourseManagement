package am.mainserver.coursemanagement.repository;

import am.mainserver.coursemanagement.domain.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image,Long> {

}
