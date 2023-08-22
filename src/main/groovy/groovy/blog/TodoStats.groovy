package groovy.blog

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@JsonCreator
record TodoStats(@JsonProperty('total') int total,
                 @JsonProperty('completed') int completed,
                 @JsonProperty('totalScheduled') int totalScheduled,
                 @JsonProperty('completedOnSchedule') int completedOnSchedule) {}
