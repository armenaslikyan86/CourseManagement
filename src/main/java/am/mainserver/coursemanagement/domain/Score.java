package am.mainserver.coursemanagement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "score")
@Getter
@Setter
@NoArgsConstructor
public class Score {

    @Id
    @SequenceGenerator(name = "score-generator", sequenceName = "score-sequence")
    @GeneratedValue(generator = "score-generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "attendance")
    private Integer attendance;

    @Column(name = "knowledge")
    private Integer knowledge;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}
