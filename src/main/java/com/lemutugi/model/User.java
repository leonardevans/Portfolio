package com.lemutugi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemutugi.audit.Auditable;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.payload.dto.MyAccountDto;
import com.lemutugi.payload.dto.UserDto;
import com.lemutugi.payload.request.SignUpRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "mobile", "email", "id"}))
public class User extends Auditable<String>{

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;


    private String password;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = false)
    private boolean email_verified = false;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE
    })
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Location location;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
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

    public User(UserDto userDto, Set<Role> roles) {
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.enabled = userDto.isEnabled();
        this.email_verified = userDto.isEmail_verified();
        this.fName = userDto.getFName();
        this.lName = userDto.getLName();
        this.tagline = userDto.getTagline();
        this.bio = userDto.getBio();
        this.whatIDo = userDto.getWhatIDo();
        this.twitterUrl = userDto.getTwitterUrl();
        this.githubUrl = userDto.getGithubUrl();
        this.linkedInUrl = userDto.getLinkedInUrl();
        this.stackoverflowUrl = userDto.getStackoverflowUrl();
        this.codePenUrl = userDto.getCodePenUrl();
        this.company = userDto.getCompany();
        this.website = userDto.getWebsite();
        this.mobile = userDto.getMobile();
        this.careerSummary = userDto.getCareerSummary();
        this.accountNonExpired = userDto.getAccountNonExpired();
        this.accountNonLocked = userDto.getAccountNonLocked();
        this.credentialsNonExpired = userDto.getCredentialsNonExpired();
        this.provider = userDto.getProvider();
        this.providerId = userDto.getProviderId();
        this.roles = roles;
    }

    public void update(UserDto userDto, Set<Role> roles){
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.enabled = userDto.isEnabled();
        this.email_verified = userDto.isEmail_verified();
        this.fName = userDto.getFName();
        this.lName = userDto.getLName();
        this.tagline = userDto.getTagline();
        this.bio = userDto.getBio();
        this.whatIDo = userDto.getWhatIDo();
        this.twitterUrl = userDto.getTwitterUrl();
        this.githubUrl = userDto.getGithubUrl();
        this.linkedInUrl = userDto.getLinkedInUrl();
        this.stackoverflowUrl = userDto.getStackoverflowUrl();
        this.codePenUrl = userDto.getCodePenUrl();
        this.company = userDto.getCompany();
        this.website = userDto.getWebsite();
        this.mobile = userDto.getMobile();
        this.careerSummary = userDto.getCareerSummary();
        this.accountNonExpired = userDto.getAccountNonExpired();
        this.accountNonLocked = userDto.getAccountNonLocked();
        this.credentialsNonExpired = userDto.getCredentialsNonExpired();
        this.provider = userDto.getProvider();
        this.providerId = userDto.getProviderId();
        this.roles = roles;
    }

    public void update(MyAccountDto myAccountDto){
        this.username = myAccountDto.getUsername();
        this.email = myAccountDto.getEmail();
        this.fName = myAccountDto.getFName();
        this.lName = myAccountDto.getLName();
        this.tagline = myAccountDto.getTagline();
        this.bio = myAccountDto.getBio();
        this.whatIDo = myAccountDto.getWhatIDo();
        this.twitterUrl = myAccountDto.getTwitterUrl();
        this.githubUrl = myAccountDto.getGithubUrl();
        this.linkedInUrl = myAccountDto.getLinkedInUrl();
        this.stackoverflowUrl = myAccountDto.getStackoverflowUrl();
        this.codePenUrl = myAccountDto.getCodePenUrl();
        this.company = myAccountDto.getCompany();
        this.website = myAccountDto.getWebsite();
        this.mobile = myAccountDto.getMobile();
        this.careerSummary = myAccountDto.getCareerSummary();
    }
}
