package groovy.blog

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.Valid
import java.time.LocalDate

@Repository
interface TodoRepository extends CrudRepository<@Valid Todo, @Valid TodoKey> {
    int countByCompletedIsNotNull()

    int countByDueNotEqual(LocalDate due)

    @Query('SELECT count(*) FROM Todo t WHERE t.completed <= t.key.due')
    int countCompletedOnSchedule()
}
