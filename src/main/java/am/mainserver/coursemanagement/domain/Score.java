package am.mainserver.coursemanagement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import javax.persistence.*;

@Entity
@Table(name = "score")
@Getter
@Setter
@NoArgsConstructor
public class Score {

    @Id
    @SequenceGenerator(name = "score_generator", sequenceName = "score_sequence", allocationSize = 1)
    @GeneratedValue(generator = "score_generator", strategy = GenerationType.SEQUENCE)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        return new EqualsBuilder()
                .append(id, score.id)
                .append(attendance, score.attendance)
                .append(knowledge, score.knowledge)
                .append(user, score.user)
                .append(course, score.course)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(attendance)
                .append(knowledge)
                .append(user)
                .append(course)
                .toHashCode();
    }
}
