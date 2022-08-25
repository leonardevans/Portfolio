package com.lemutugi.payload.dto;

import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.validation.constraint.PhoneNumber;
import com.lemutugi.validation.constraint.Name;
import com.lemutugi.validation.constraint.ShortDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username should contain at least 3 characters")
    @Size(max = 35, message = "Username should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should contain only letters and numbers. No special characters.")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email format")
    private String email;

    private String password;
    private String confirmPassword;

    @NotBlank(message = "First name is required")
    @Size(min = 3, message = "First name should contain at least 3 characters")
    @Size(max = 35, message = "First name should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters")
    private String fName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, message = "Last name should contain at least 3 characters")
    @Size(max = 35, message = "Last name should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    private String lName;

    @Name(message = "Tagline should contain at least 3 characters and should not exceed 50 characters")
    private String tagline;

    @ShortDescription(message = "Bio should contain at least 50 characters and should not exceed 300 characters ")
    private String bio;

    private String profilePic;

    @ShortDescription(message = "What I do should contain at least 50 characters and should not exceed 300 characters ")
    private String whatIDo;

    private String twitterUrl;
    private String githubUrl;
    private String linkedInUrl;
    private String stackoverflowUrl;
    private String codePenUrl;
    private String website;

    @Name(message = "Company name should contain at least 3 characters and should not exceed 50 characters")
    private String company;

    @PhoneNumber(message = "Please provide a valid mobile number")
    private Long mobile;

    @ShortDescription(message = "Career summary should contain at least 50 characters and should not exceed 300 characters ")
    private String careerSummary;

    @NotNull(message = "Please select provider")
    private AuthProvider provider;

    @Name(message = "Provider id should contain at least 3 characters and should not exceed 50 characters")
    private String providerId;

    private boolean enabled = false;
    private boolean email_verified = false;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;

    private Set<Role> roles = new HashSet<>();

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fName = user.getFName();
        this.lName = user.getLName();
        this.tagline = user.getTagline();
        this.bio = user.getBio();
        this.profilePic = user.getProfilePic();
        this.enabled = user.isEnabled();
        this.email_verified = user.isEmail_verified();
        this.whatIDo = user.getWhatIDo();
        this.twitterUrl = user.getTwitterUrl();
        this.githubUrl = user.getGithubUrl();
        this.linkedInUrl = user.getLinkedInUrl();
        this.stackoverflowUrl = user.getStackoverflowUrl();
        this.codePenUrl = user.getCodePenUrl();
        this.company = user.getCompany();
        this.website = user.getWebsite();
        this.mobile = user.getMobile();
        this.careerSummary = user.getCareerSummary();
        this.accountNonExpired = user.getAccountNonExpired();
        this.accountNonLocked = user.getAccountNonLocked();
        this.credentialsNonExpired = user.getCredentialsNonExpired();
        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
        this.roles = user.getRoles();
    }
}
