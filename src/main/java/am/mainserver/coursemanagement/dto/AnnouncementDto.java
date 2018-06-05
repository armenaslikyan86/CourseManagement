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
public class AnnouncementDto {

    private Long id;

    private String title;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AnnouncementDto that = (AnnouncementDto) o;

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
