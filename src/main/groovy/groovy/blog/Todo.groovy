package groovy.blog

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.EqualsAndHashCode
import io.micronaut.core.annotation.*
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

@Entity
@Serdeable
@EqualsAndHashCode
@JsonIgnoreProperties(['done', 'scheduled'])
class Todo {
    @NonNull
    @EmbeddedId
    TodoKey key

    @Nullable
    String description

    @Nullable
    LocalDate completed

    Todo() {}

    Todo(@NotNull @Valid TodoKey key, @Nullable String description, @Nullable LocalDate completed) {
        this.key = key
        this.description = description
        this.completed = completed
    }

    Todo(@NotNull @NotBlank String title, @Nullable String description, @Nullable LocalDate due, @Nullable LocalDate completed) {
        this.key = new TodoKey(title, due)
        this.description = description
        this.completed = completed
    }

    boolean isDone() {
        completed != null
    }

    boolean isScheduled() {
        key.scheduled
    }

    String toString() {
        "Todo($key.title, $description, $key.due, $completed)"
    }
}
