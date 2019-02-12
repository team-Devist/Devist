package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private int id;
    private String title;
    private String description;
    @MapsId
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "todo_id")
    private RepeatDay repeatDay;
    private LocalDateTime createdTime;
    private double doneRate = 0.0;
    @OneToOne
    @JoinColumn(name = "latest_daily_check_id")
    private DailyCheck latestDailyCheck;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<DailyCheck> dailyChecks = new ArrayList<>();

    public boolean addDailyCheck(DailyCheck dailyCheck) {
        return dailyChecks.add(dailyCheck);
    }

    public boolean isDone() {
        if (latestDailyCheck == null) {
            // Todo: latestDaliyCheck 가 없다는 로그 출력
            return false;
        }
        return latestDailyCheck.isDone();
    }

    public boolean isTodaysTodo() {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        return (((FixedRepeatDay) repeatDay).getDaysOfWeek() & (1 << (dayOfWeek - 1))) > 0;
    }

    public boolean[] getCheckboxsArr() {
        return ((FixedRepeatDay) repeatDay).getCheckboxs();
    }

    public String[] getWeekString() {
        return ((FixedRepeatDay) repeatDay).getWEEK_DAY_STR();
    }
}
