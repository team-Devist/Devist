package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    @OneToOne
    @JoinColumn(name = "todo_id")
    private RepeatDay repeatDay = new FixedRepeatDay();
    private LocalDateTime createdTime;
    private double doneRate = 0.0;
    @OneToOne
    @JoinColumn(name = "latest_daily_check_id")
    private DailyCheck latestDailyCheck;

    @Transient
    private boolean[] repeatCheckbox = {true, true, true, true, true, true, true};
    @Transient
    private final String[] WEEK_DAY = {"일", "월", "화", "수", "목", "금", "토"};

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

    /**
     * @author delf
     * <p>
     * view에서 받아 저장된 {@link #repeatCheckbox}을 byte로 변환하여 {@link #repeatDay}에 저장합니다.
     * 이슈 #17을 참고할 것.
     */
    public void convertRepeatDayBooleanArrToByte() {
        byte rd = 0;
        for (int i = repeatCheckbox.length - 1; i >= 0; i--) {
            rd |= repeatCheckbox[i] ? (byte) (1 << (repeatCheckbox.length - 1) - i) : 0;
        }
        ((FixedRepeatDay) repeatDay).setDayOfWeek(rd);
    }

    public void convertRepeatDayByteToBooleanArr() {
        for (int i = 0; i < repeatCheckbox.length; i++) {
            repeatCheckbox[repeatCheckbox.length - 1 - i] = ((((FixedRepeatDay) repeatDay).getDayOfWeek() >> i) & 1) == 1;
        }
    }

    public boolean isTodaysTodo() {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        return (((FixedRepeatDay) repeatDay).getDayOfWeek() & (1 << (dayOfWeek - 1))) > 0;
    }

    public void setRepeatDay(byte rd) {
        ((FixedRepeatDay) repeatDay).setDayOfWeek(rd);
    }

    public byte getRepeatDay() {
        return ((FixedRepeatDay) repeatDay).getDayOfWeek();
    }

//    public void setTodoInRepeatDay() {
//        repeatDay.setTodo(this);
//    }
}
