package groovy.blog

import groovy.transform.CompileStatic
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Inject

import java.time.LocalDate

import static io.micronaut.scheduling.TaskExecutors.BLOCKING

@CompileStatic
@ExecuteOn(BLOCKING)
@Controller('todo')
class TodoController {
    @Inject TodoService service

    @Get('/')
    Collection<Todo> list() {
        service.findAll()
    }

    @Post('/')
    @Status(HttpStatus.CREATED)
    Todo create(@Body Todo todo) {
        service.create(todo)
    }

    @Post('/find')
    Todo find(@Body TodoKey key) {
        service.find(key)
    }

    @Get('/stats')
    TodoStats statsAsString() {
        service.stats()
    }

    @Delete('/delete/{title}')
    Todo delete(@PathVariable String title) {
        delete(title, null)
    }

    @Delete('/delete/{title}/{due}')
    Todo delete(@PathVariable String title, @PathVariable LocalDate due) {
        service.delete(new TodoKey(title, due))
    }

    @Put('/reschedule/{newDate}')
    Todo reschedule(@Body TodoKey key, @PathVariable LocalDate newDate) {
        service.reschedule(key, newDate)
    }

    @Put('/unschedule')
    Todo unschedule(@Body TodoKey key) {
        service.unschedule(key)
    }

    @Put('/complete')
    Todo complete(@Body TodoKey key) {
        service.complete(key)
    }
}
