package am.mainserver.coursemanagement.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "announcement")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Announcement {
    @Id
    @SequenceGenerator(name = "announcement-generator", sequenceName = "announcement-sequence")
    @GeneratedValue(generator = "announcement-generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
}
