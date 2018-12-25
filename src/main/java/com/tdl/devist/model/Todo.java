package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_id")
    private int id;
    private String title;
    private String content;
    private double doneRate;
    private byte repeatDay;
    private boolean isDone;
    private LocalDateTime createdTime;

    @ManyToOne (cascade = CascadeType.ALL) // casecade 옵션 다시보기
    @JoinColumn(name = "user_name")
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private Set<DailyCheck> dailyChecks = new HashSet<>();

    public boolean addDailyCheck(DailyCheck dailyCheck) {
        return dailyChecks.add(dailyCheck);
    }
}
