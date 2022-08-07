package com.lemutugi.model;

import com.lemutugi.audit.Auditable;
import com.lemutugi.model.enums.AuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "mobile", "email", "id"}))
public class User extends Auditable<String>{

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Location location;
}
