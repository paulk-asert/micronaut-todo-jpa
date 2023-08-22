package groovy.blog

import io.micronaut.context.BeanContext
import io.micronaut.serde.SerdeIntrospections
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import io.micronaut.core.type.Argument

@MicronautTest(startApplication = false)
class TodoStatsSpec extends Specification {

    @Inject
    BeanContext beanContext

    void "TodoStats is annotated with @Serdeable.Deserializable"() {
        given:
        SerdeIntrospections serdeIntrospections = beanContext.getBean(SerdeIntrospections)

        when:
        serdeIntrospections.getDeserializableIntrospection(Argument.of(TodoStats))

        then:
        noExceptionThrown()
    }

    void "TodoStats is annotated with @Serdeable.Serializable"() {
        given:
        SerdeIntrospections serdeIntrospections = beanContext.getBean(SerdeIntrospections)

        when:
        serdeIntrospections.getSerializableIntrospection(Argument.of(TodoStats))

        then:
        noExceptionThrown()
    }
}
