package am.mainserver.coursemanagement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class CourseDto {
    private Long id;

    private String name;

    private Integer duration;

    private String description;

    private Double price;

    private Set<UserDto> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CourseDto courseDto = (CourseDto) o;

        return new EqualsBuilder()
                .append(id, courseDto.id)
                .append(name, courseDto.name)
                .append(duration, courseDto.duration)
                .append(description, courseDto.description)
                .append(price, courseDto.price)
                .append(users, courseDto.users)
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
                .append(users)
                .toHashCode();
    }
}
