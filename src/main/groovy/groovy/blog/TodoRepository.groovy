package groovy.blog

import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.Valid
import java.time.LocalDate

@CompileStatic
@Repository
abstract class TodoRepository implements CrudRepository<@Valid Todo, @Valid TodoKey> {
    abstract long countByCompletedIsNotNull()

    abstract long countByDueNotEqual(LocalDate due)

    @Query('SELECT count(*) FROM Todo t WHERE t.completed <= t.key.due')
    abstract long countCompletedOnSchedule()

    Todo findByKey(TodoKey key) {
        findById(key).get()
    }
}
