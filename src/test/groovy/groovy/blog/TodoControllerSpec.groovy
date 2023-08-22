package groovy.blog

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.json.JsonMapper
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import java.time.LocalDate

@MicronautTest
class TodoControllerSpec extends Specification {

    @Inject
    @Client("/todo")
    HttpClient client

    @Inject
    JsonMapper mapper

    void "test stats response"() {
        when:
        var request = HttpRequest.GET('/stats')
        var json = client.toBlocking().retrieve(request, String)
        var stats = mapper.readValue(json, TodoStats)

        then:
        stats.toMap() == [total: 0, completed: 0, totalScheduled: 0, completedOnSchedule: 0]
    }

    void "test create, create, list, find, delete, complete, unschedule, reschedule"() {
        when:
        var todo = new Todo('Urgent call', null, null, null)
        var request = HttpRequest.POST('/', todo)
        var created = client.toBlocking().retrieve(request, Todo)

        then:
        created == todo

        when:
        todo = new Todo('Buy champagne!!', null, LocalDate.of(1999, 12, 31), null)
        request = HttpRequest.POST('/', todo)
        created = client.toBlocking().retrieve(request, Todo)

        then:
        created == todo

        when:
        request = HttpRequest.GET('/')
        var list = client.toBlocking().retrieve(request, String)

        then:
        list == '[{"key":{"title":"Urgent call","due":"1900-01-01"}},{"key":{"title":"Buy champagne!!","due":"1999-12-31"}}]'

        when:
        var key = new TodoKey('Urgent call')
        request = HttpRequest.POST('/find', key)
        todo = client.toBlocking().retrieve(request, Todo)

        then:
        todo.key.title == 'Urgent call'
        !todo.scheduled

        when:
        request = HttpRequest.GET('/stats')
        var json = client.toBlocking().retrieve(request, String)
        var stats = mapper.readValue(json, TodoStats)

        then:
        stats.toMap() == [total: 2, completed: 0, totalScheduled: 1, completedOnSchedule: 0]

        when:
        request = HttpRequest.DELETE('/delete/Urgent%20call')
        var removed = client.toBlocking().retrieve(request, Todo)

        then:
        removed == todo

        when:
        key = new TodoKey('Buy champagne!!', LocalDate.of(1999, 12, 31))
        request = HttpRequest.PUT('/complete', key)
        client.toBlocking().retrieve(request)
        request = HttpRequest.PUT('/unschedule', key)
        var updated = client.toBlocking().retrieve(request, Todo)

        then:
        updated.key.title == key.title
        !updated.scheduled
        updated.done

        when:
        request = HttpRequest.GET('/stats')
        json = client.toBlocking().retrieve(request, String)
        stats = mapper.readValue(json, TodoStats)

        then:
        stats.toMap() == [total: 1, completed: 1, totalScheduled: 0, completedOnSchedule: 0]

        when:
        key = new TodoKey('Buy champagne!!')
        request = HttpRequest.PUT('/reschedule/2023-12-31', key)
        updated = client.toBlocking().retrieve(request, Todo)

        then:
        updated.key.title == key.title
        updated.scheduled
        updated.done

        when:
        request = HttpRequest.GET('/stats')
        json = client.toBlocking().retrieve(request, String)
        stats = mapper.readValue(json, TodoStats)

        then:
        stats.toMap() == [total: 1, completed: 1, totalScheduled: 1, completedOnSchedule: 1]
    }

}
