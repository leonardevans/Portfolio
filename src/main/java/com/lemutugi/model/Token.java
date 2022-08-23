package com.lemutugi.model;

import com.lemutugi.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Token extends Auditable<String> {
    @Column(name = "token")
    private String token;

    private String type;
    private boolean used = false;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public Token(User user, String type) {
        this.type = type;
        this.user = user;
        this.token = java.util.UUID.randomUUID().toString();
    }
}
