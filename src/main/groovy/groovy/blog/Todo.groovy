package groovy.blog

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import io.micronaut.core.annotation.*
import io.micronaut.core.util.StringUtils
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

import java.time.LocalDate

@CompileStatic
@Introspected
@Entity
@Serdeable
@EqualsAndHashCode
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
    Todo(@NonNull TodoKey key, @Nullable String description, @Nullable LocalDate completed) {
        this.key = key
        this.description = description
        this.completed = completed
    }

    Todo(@NonNull String title, @Nullable String description, @Nullable LocalDate due, @Nullable LocalDate completed) {
        this(StringUtils.isEmpty(title) ? null : new TodoKey(title, due), description, completed)
    }

    boolean isDone() {
        completed != null
    }

    boolean isScheduled() {
        key?.scheduled
    }

    String toString() {
        "Todo($key.title, $description, $key.due, $completed)"
    }
}
