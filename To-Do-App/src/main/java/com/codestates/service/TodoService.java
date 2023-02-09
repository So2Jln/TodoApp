package com.codestates.service;

import com.codestates.entity.Todo;
import com.codestates.exception.BusinessLogicException;
import com.codestates.exception.ExceptionCode;
import com.codestates.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo){
        Todo savedTodo =  todoRepository.save(todo);

        return savedTodo;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Todo updateTodo(Todo todo){
        Todo findTodo = findVerifiedTodo(todo.getId());

        Optional.ofNullable(todo.getTitle())
                .ifPresent(title->findTodo.setTitle(title));
        Optional.ofNullable(todo.getTodo_order())
                .ifPresent(order->findTodo.setTodo_order(order));
        Optional.ofNullable(todo.isCompleted())
                .ifPresent(completed->findTodo.setCompleted(completed));

        return todoRepository.save(findTodo);
    }


    @Transactional(readOnly = true)
    public Todo findTodo(int id){
        return findVerifiedTodo(id);
    }

//    public Page<Todo> findTodos(int page, int size){
//        return todoRepository.findAll(PageRequest.of(page,size,
//                Sort.by("id").descending()));
//    }
    public List<Todo> findTodos(){
        return todoRepository.findAll(Sort.by("id").descending());
    }

    public void deleteTodo(int id){
        Todo findTodo = findVerifiedTodo(id);

        todoRepository.delete(findTodo);
    }

    public void deleteTodos(){
        todoRepository.deleteAll();
    }

    public Todo findVerifiedTodo(int id){
        Optional<Todo> optionalTodo =
                todoRepository.findById(id);
        Todo findTodo=
                optionalTodo.orElseThrow(()->
                        new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
        return findTodo;
    }
}
