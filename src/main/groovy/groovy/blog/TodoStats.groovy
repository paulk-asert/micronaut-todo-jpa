package groovy.blog

import groovy.transform.Canonical
import io.micronaut.serde.annotation.Serdeable

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
