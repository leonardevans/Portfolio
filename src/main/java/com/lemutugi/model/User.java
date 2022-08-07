package com.lemutugi.model;

import com.lemutugi.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "mobile", "email", "id"}))

public class User extends Auditable<String>{
    private String username;
    private String email;
    private String password;
    private boolean enabled = false;
    private boolean email_verified = false;
    private String fName;
    private String lName;
    private String tagline;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String profilePic;

    @Column(columnDefinition = "TEXT")
    private String whatIDo;

    private String twitterUrl;
    private String githubUrl;
    private String linkedInUrl;
    private String stackoverflowUrl;
    private String codePenUrl;
    private String company;
    private String website;
    private Long mobile;

    @Column(columnDefinition = "TEXT")
    private String careerSummary;

    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Location location;
}
