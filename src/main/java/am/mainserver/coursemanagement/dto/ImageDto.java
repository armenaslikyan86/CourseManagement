package am.mainserver.coursemanagement.dto;

import am.mainserver.coursemanagement.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageDto {

    private Long id;

    private String imageUrl;

    private User user;
}
