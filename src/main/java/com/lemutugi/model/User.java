package com.lemutugi.model;

import com.lemutugi.audit.Auditable;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.payload.request.SignUpRequest;
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

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
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

    @Column(unique = true)
    private Long mobile;

    @Column(columnDefinition = "TEXT")
    private String careerSummary;

    @Column(nullable = false)
    private Boolean accountNonExpired = true;

    @Column(nullable = false)
    private Boolean accountNonLocked = true;

    @Column(nullable = false)
    private Boolean credentialsNonExpired = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Location location;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Token token;

    public User(SignUpRequest signUpRequest, String password, boolean enabled, boolean email_verified, AuthProvider provider) {
        this.username = signUpRequest.getUsername();
        this.email = signUpRequest.getEmail();
        this.password = password;
        this.enabled = enabled;
        this.email_verified = email_verified;
        this.fName = signUpRequest.getFName();
        this.lName = signUpRequest.getLName();
        this.provider = provider;
    }
}
