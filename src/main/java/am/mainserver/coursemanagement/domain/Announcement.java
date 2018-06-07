package am.mainserver.coursemanagement.domain;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "announcement")
@Getter
@Setter
@NoArgsConstructor
public class Announcement {
    @Id
    @SequenceGenerator(name = "announcement_generator", sequenceName = "announcement_sequence", allocationSize = 1)
    @GeneratedValue(generator = "announcement_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Announcement that = (Announcement) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(title, that.title)
                .append(description, that.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .append(description)
                .toHashCode();
    }
}
