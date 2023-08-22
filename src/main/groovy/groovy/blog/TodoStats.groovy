package groovy.blog

import io.micronaut.serde.annotation.Serdeable

@Serdeable
record TodoStats(int total,
                 int completed,
                 int totalScheduled,
                 int completedOnSchedule) {}
