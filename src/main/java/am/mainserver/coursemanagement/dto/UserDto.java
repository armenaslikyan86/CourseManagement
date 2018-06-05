package am.mainserver.coursemanagement.dto;

import am.mainserver.coursemanagement.common.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;

    private String title;

    private String firstName;

    private String lastName;

    private Integer age;

    private String email;

    private String description;

    private String phoneNumber;

    private RoleType roleType;

    private Set<CourseDto> courses = new HashSet<>();

    private Map<CourseDto, ScoreDto> courseScoreMap = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(id, userDto.id)
                .append(title, userDto.title)
                .append(firstName, userDto.firstName)
                .append(lastName, userDto.lastName)
                .append(age, userDto.age)
                .append(email, userDto.email)
                .append(description, userDto.description)
                .append(phoneNumber, userDto.phoneNumber)
                .append(roleType, userDto.roleType)
                .append(courses, userDto.courses)
                .append(courseScoreMap, userDto.courseScoreMap)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .append(firstName)
                .append(lastName)
                .append(age)
                .append(email)
                .append(description)
                .append(phoneNumber)
                .append(roleType)
                .append(courses)
                .append(courseScoreMap)
                .toHashCode();
    }
}
