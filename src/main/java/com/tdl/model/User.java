package com.tdl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {
    // TODO: 각 입력 값에 대한 길이 제한
    @Id
    @Column(name="user_id")
    private String id;
    private String password; // DB에 varchar 길이 설정은 어떻게하지
    private String name;

    private double doneRate;
}
