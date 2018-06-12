package am.mainserver.coursemanagement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "image")
@NoArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @SequenceGenerator(name = "course_generator", sequenceName = "course_sequence",allocationSize = 1)
    @GeneratedValue(generator = "course_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
