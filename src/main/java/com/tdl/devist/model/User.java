package com.tdl.devist.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, length=200)
    private String password;
    @Column(columnDefinition = "int default 1")
    private int enabled = 1;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Authority> authorities = new ArrayList<>();

    @Column
    private String name;
    @Column(columnDefinition = "Decimal(10,2) default '100.00'")
    private double doneRate = 100.00;

    public void addAuthority(Authority authority) {
        authorities.add(authority);
    }
}
