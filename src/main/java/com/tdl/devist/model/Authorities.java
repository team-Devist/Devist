package com.tdl.devist.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Authorities {
    @EmbeddedId AuthorityId id;
}

@Embeddable
class AuthorityId implements Serializable {
    String username;
    String authority;
}



