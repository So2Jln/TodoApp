package com.codestates.controller;

import com.codestates.dto.TodoPatchDto;
import com.codestates.dto.TodoPostDto;
import com.codestates.entity.Todo;
import com.codestates.mapper.TodoMapper;
import com.codestates.response.MultiResponseDto;
import com.codestates.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
public class todoController {
    private final TodoMapper mapper;
    private final TodoService todoService;

    public todoController(TodoMapper mapper, TodoService todoService) {
        this.mapper = mapper;
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity postTodo(@RequestBody TodoPostDto requestBody){
        Todo todo = mapper.todoPostToTodo(requestBody);

        Todo createdTodo = todoService.createTodo(todo);

        return new ResponseEntity<>(mapper.TodoToTodoResponseDto(createdTodo),
                HttpStatus.CREATED);

    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive int id,
                                    @RequestBody TodoPatchDto requestBody){
        requestBody.setId(id);

        Todo todo =
                todoService.updateTodo(mapper.todoPatchToTodo(requestBody));

        return new ResponseEntity<>(mapper.TodoToTodoResponseDto(todo),
                HttpStatus.OK);
    }

    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") @Positive int id){
        Todo todo = todoService.findTodo(id);
        return new ResponseEntity<>(mapper.TodoToTodoResponseDto(todo),
                HttpStatus.OK);
    }

    @GetMapping
//    public ResponseEntity getTodos(@Positive @RequestParam int page,
//                                   @Positive @RequestParam int size){
    public ResponseEntity getTodos(){
//        Page<Todo> todoPages = todoService.findTodos(page-1,size);
        List<Todo> todos = todoService.findTodos();
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(mapper.TodosTotodoResponses(todos),todoPages),HttpStatus.OK);
        return new ResponseEntity<>(todos,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteTodos(){
        todoService.deleteTodos();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id")@Positive int id){
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
