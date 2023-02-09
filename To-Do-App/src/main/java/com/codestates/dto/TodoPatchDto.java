package com.codestates.dto;

import lombok.Getter;

@Getter
public class TodoPatchDto {
    private int id;

    private String title;

    private int todo_order;

    private boolean completed;

    public void setId(int id){
        this.id = id;
    }
}
