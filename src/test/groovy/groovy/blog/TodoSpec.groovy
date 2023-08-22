package groovy.blog

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.validation.Validator
import spock.lang.Specification

import java.time.LocalDate

@MicronautTest(startApplication = false)
class TodoSpec extends Specification {
    private static final LocalDate ANY_DATE = LocalDate.now()
    private static final String ANY_DESC = 'Some description'

    @Inject
    Validator validator

    void "key is required"() {
        given:
        Todo todo = new Todo(null, ANY_DESC, ANY_DATE, ANY_DATE)
        expect:
        !validator.validate(todo).isEmpty()
    }

}
