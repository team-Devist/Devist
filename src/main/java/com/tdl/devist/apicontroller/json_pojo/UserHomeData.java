package com.tdl.devist.apicontroller.json_pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserHomeData {
    private int todoSize;
    private int completedTodoSize;
    private double userDoneRate;
}
