package com.codestates.mapper;

import com.codestates.dto.TodoPatchDto;
import com.codestates.dto.TodoPostDto;
import com.codestates.dto.TodoResponseDto;
import com.codestates.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {
    Todo todoPostToTodo(TodoPostDto requestBody);
    Todo todoPatchToTodo(TodoPatchDto requestBody);
    TodoResponseDto TodoToTodoResponseDto(Todo todo);
    List<TodoResponseDto> TodosTotodoResponses(List<Todo> todos);
}
