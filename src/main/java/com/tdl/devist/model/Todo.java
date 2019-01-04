package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    @Column(length = 1)
    private byte repeatDay = 127;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDone = false;
    private LocalDateTime createdTime;
    private double doneRate = 0.0;

    @Transient
    private boolean[] repeatCheckbox = {true, true, true, true, true, true, true};
    @Transient
    private final static String[] WEEK_DAY = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<DailyCheck> dailyChecks = new ArrayList<>();

    public boolean addDailyCheck(DailyCheck dailyCheck) {
        return dailyChecks.add(dailyCheck);
    }

    /**
     * @author delf
     * <p>
     * view에서 받아 저장된 {@link #repeatCheckbox}을 byte로 변환하여 {@link #repeatDay}에 저장합니다.
     */
    public void convertRepeatDayBooleanArrToByte() {
        StringBuilder res = new StringBuilder();
        for (boolean b : repeatCheckbox) {
            res.append(b ? 1 : 0);
        }
        repeatDay = Byte.parseByte(res.toString(), 2);
    }
}
