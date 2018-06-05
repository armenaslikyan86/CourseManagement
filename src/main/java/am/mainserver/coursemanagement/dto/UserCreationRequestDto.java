package am.mainserver.coursemanagement.dto;

import am.mainserver.coursemanagement.common.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@NoArgsConstructor
@Getter
@Setter
public class UserCreationRequestDto {

    private String title;

    private String firstName;

    private String lastName;

    private Integer age;

    private String email;

    private String passwordHash;

    private String phoneNumber;

    private RoleType roleType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserCreationRequestDto that = (UserCreationRequestDto) o;

        return new EqualsBuilder()
                .append(title, that.title)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(age, that.age)
                .append(email, that.email)
                .append(passwordHash, that.passwordHash)
                .append(phoneNumber, that.phoneNumber)
                .append(roleType, that.roleType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(firstName)
                .append(lastName)
                .append(age)
                .append(email)
                .append(passwordHash)
                .append(phoneNumber)
                .append(roleType)
                .toHashCode();
    }
}
