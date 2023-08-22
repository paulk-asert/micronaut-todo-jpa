package groovy.blog

import groovy.transform.CompileStatic
import jakarta.inject.Inject
import jakarta.inject.Singleton
import jakarta.validation.Valid

import java.time.LocalDate

@CompileStatic
@Singleton
class TodoService {
    @Inject TodoRepository repo

    Collection<Todo> findAll() {
        repo.findAll()
    }

    Todo create(@Valid Todo todo) {
        repo.save(todo)
    }

    Todo find(@Valid TodoKey key) {
        repo.findById(key).get()
    }

    TodoStats stats() {
        long total = repo.count()
        int completed = repo.countByCompletedIsNotNull()
        int totalScheduled = repo.countByDueNotEqual(TodoKey.NULL)
        int completedOnSchedule = repo.countCompletedOnSchedule()
        new TodoStats(total.intValue(), completed, totalScheduled, completedOnSchedule)
    }

    Todo delete(@Valid TodoKey key) {
        Todo todo = find(key)
        repo.delete(todo)
        todo
    }

    Todo reschedule(@Valid TodoKey key, LocalDate newDate) {
        Todo todo = find(key)
        repo.delete(todo)
        todo.key = new TodoKey(key.title, newDate)
        repo.save(todo)
    }

    Todo unschedule(@Valid TodoKey key) {
        Todo todo = find(key)
        if (todo.scheduled) {
            repo.delete(todo)
            todo.key = new TodoKey(key.title)
            todo = repo.save(todo)
        }
        todo
    }

    Todo complete(@Valid TodoKey key) {
        Todo todo = find(key).tap { completed = LocalDate.now() }
        repo.update(todo)
    }
}
