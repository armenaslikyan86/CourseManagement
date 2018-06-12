package am.mainserver.coursemanagement.domain;

import am.mainserver.coursemanagement.common.RoleType;
import com.google.common.collect.Lists;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "`user`")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    private static final long serialVersionUID = -8632813353208855706L;

    @Id
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(generator = "user_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_course",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id", unique = true) }
    )
    private Set<Course> courses = new HashSet<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Image image;

//    @ManyToMany
//    @JoinTable(name = "user_course_score",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "score_id"))
//    @MapKeyJoinColumn(name = "course_id")
//    private Map<Course, Score> courseScoreMap = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Score> scores = new HashSet<>();

    public User(final String email, final String passwordHash, final String firstName, final String lastName, final RoleType roleType) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roleType = roleType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Lists.newArrayList(new SimpleGrantedAuthority("ROLE_" + roleType.name()));
    }

    @Override
    public String getPassword() {
        return getPasswordHash();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(title, user.title)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(age, user.age)
                .append(email, user.email)
                .append(description, user.description)
                .append(phoneNumber, user.phoneNumber)
                .append(roleType, user.roleType)
                .append(passwordHash, user.passwordHash)
                .append(image, user.image)
                .append(scores, user.scores)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .append(firstName)
                .append(lastName)
                .append(age)
                .append(email)
                .append(description)
                .append(phoneNumber)
                .append(roleType)
                .append(passwordHash)
                .append(image)
                .append(scores)
                .toHashCode();
    }
}
