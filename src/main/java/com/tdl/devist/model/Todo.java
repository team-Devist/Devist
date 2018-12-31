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
    private String description;
    @Column(length = 86)
    private String[] repeatDay = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    private boolean isDone;
    private LocalDateTime createdTime;
    private double doneRate = 0.0;

    @ManyToOne(cascade = CascadeType.ALL) // casecade 옵션 다시보기
    @JoinColumn(name = "user_name")
    private User user;

    // @OneToMany(mappedBy = "todo", cascade = CascadeType.MERGE)
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<DailyCheck> dailyChecks = new ArrayList<>();

    public boolean addDailyCheck(DailyCheck dailyCheck) {
        return dailyChecks.add(dailyCheck);
    }
}
