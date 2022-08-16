package com.lemutugi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemutugi.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Privilege extends Auditable<String> {
    @Column(unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
    private Collection<Role> roles = new ArrayList<>();

    public Privilege(String name) {
        this.name = name;
    }
}
