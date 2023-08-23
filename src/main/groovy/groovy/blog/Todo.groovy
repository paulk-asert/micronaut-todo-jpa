package groovy.blog

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import io.micronaut.core.annotation.*
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

@CompileStatic
@Entity
@Serdeable
@EqualsAndHashCode
@Introspected
@JsonIgnoreProperties(['done', 'scheduled'])
class Todo {
    @NotNull
    @Valid
    @EmbeddedId
    TodoKey key

    @Nullable
    String description

    @Nullable
    LocalDate completed

    Todo() {}

    @Creator
    Todo(@NotNull @Valid TodoKey key, @Nullable String description, @Nullable LocalDate completed) {
        this.key = key
        this.description = description
        this.completed = completed
    }

    Todo(@NotBlank String title, @Nullable String description, @Nullable LocalDate due, @Nullable LocalDate completed) {
        this.key = title?.trim() ? new TodoKey(title, due) : null
        this.description = description
        this.completed = completed
    }

    boolean isDone() {
        completed != null
    }

    boolean isScheduled() {
        key?.scheduled
    }

    String toString() {
        "Todo($key?.title, $description, $key?.due, $completed)"
    }
}
