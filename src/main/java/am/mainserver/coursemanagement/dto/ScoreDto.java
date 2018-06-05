package am.mainserver.coursemanagement.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


@NoArgsConstructor
@Getter
@Setter
public class ScoreDto {
    private Long id;

    private Integer attendance;

    private Integer knowledge;

    private UserDto user;

    private CourseDto course;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ScoreDto scoreDto = (ScoreDto) o;

        return new EqualsBuilder()
                .append(id, scoreDto.id)
                .append(attendance, scoreDto.attendance)
                .append(knowledge, scoreDto.knowledge)
                .append(user, scoreDto.user)
                .append(course, scoreDto.course)
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
