package com.codestates.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TodoResponseDto {
    private int id;
    private String title;
    private int todo_order;
    private boolean completed;
}
