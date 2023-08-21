package groovy.blog

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.Valid
import java.time.LocalDate

@Repository
abstract class TodoRepository implements CrudRepository<@Valid Todo, @Valid TodoKey> {
    abstract int countByCompletedIsNotNull()

    abstract int countByDueNotEqual(LocalDate due)

    @Query('SELECT count(*) FROM Todo t WHERE t.completed <= t.key.due')
    abstract int countCompletedOnSchedule()

    Todo findByKey(TodoKey key) {
        findById(key).get()
    }
}
