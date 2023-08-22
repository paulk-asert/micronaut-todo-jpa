package groovy.blog

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

@CompileStatic
@Canonical
@Serdeable
class TodoStats {
    int total
    int completed
    int totalScheduled
    int completedOnSchedule

    Map<String, Integer> toMap() {
        [total: total, completed: completed, totalScheduled: totalScheduled, completedOnSchedule: completedOnSchedule]
    }
}
