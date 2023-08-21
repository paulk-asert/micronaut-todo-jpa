package groovy.blog

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

@Serdeable
@EqualsAndHashCode
@Embeddable
@ToString(excludes = 'scheduled')
@JsonIgnoreProperties(['scheduled'])
class TodoKey {
    public static final LocalDate NULL = LocalDate.of(1900, 1, 1)

    @NotNull @NotBlank String title
    @NotNull LocalDate due = NULL

    TodoKey() {}

    TodoKey(@NotNull @NotBlank String title, @NotNull LocalDate due = NULL) {
        this.title = title
        this.due = due ?: NULL
    }

    boolean isScheduled() {
        due != NULL
    }

}
