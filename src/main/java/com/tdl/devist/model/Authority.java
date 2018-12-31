package com.tdl.devist.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "authorities")
@NoArgsConstructor
@Setter
public class Authority {
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_DBA = "ROLE_DBA";

    private @EmbeddedId AuthorityId id;

    public Authority(String username, String role) {
        System.out.println("@@ construct authority/ username is..." + username);
        id = new AuthorityId(username, role);
    }
}

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
class AuthorityId implements Serializable {
    String username;
    @Column
    String authority;
}