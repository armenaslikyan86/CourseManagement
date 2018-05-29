package am.mainserver.coursemanagement.domain;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
@EqualsAndHashCode
@ToString
public class Course {
    @Id
    @SequenceGenerator(name = "course-generator", sequenceName = "course-sequence")
    @GeneratedValue(generator = "course-generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToMany(mappedBy = "courses")
    private Set<User> users = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private Set<Score> scores = new HashSet<>();
}

