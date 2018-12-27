package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private int id;
    private String title;
    private String content;
    private double doneRate;
    private byte repeatDay;
    // private boolean[] repeatDay; // TODO: 자료형 고민
    private boolean isDone;
    private LocalDateTime createdTime;

    @ManyToOne (cascade = CascadeType.MERGE) // casecade 옵션 다시보기
    @JoinColumn(name = "user_name")
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    // @OneToMany(mappedBy = "todo", cascade = CascadeType.MERGE)
    private List<DailyCheck> dailyChecks = new ArrayList<>();

    public boolean addDailyCheck(DailyCheck dailyCheck) {
        return dailyChecks.add(dailyCheck);
    }
}
