package am.mainserver.coursemanagement.domain;


import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.DATE;

//
//@Entity 
//
//@Table( 
//        name = "language", 
//        uniqueConstraints = { 
//        @UniqueConstraint(name = "afasfsa", columnNames = {"short_code_2", "deleted"})
//        }
//         )

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @SequenceGenerator(name = "course_generator", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(generator = "course_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;


    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Set<Score> scores = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return new EqualsBuilder()
                .append(id, course.id)
                .append(name, course.name)
                .append(duration, course.duration)
                .append(description, course.description)
                .append(price, course.price)
                .append(users, course.users)
                .append(scores, course.scores)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(duration)
                .append(description)
                .append(price)
                .append(scores)
                .toHashCode();
    }
}

