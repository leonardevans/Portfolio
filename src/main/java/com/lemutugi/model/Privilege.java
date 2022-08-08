package com.lemutugi.model;

import com.lemutugi.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Privilege extends Auditable<Long> {
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public Privilege(String name) {
        this.name = name;
    }
}
