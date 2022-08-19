package com.lemutugi.payload.dto;

import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username is too short")
    @Size(max = 35, message = "Username is too long")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should contain only letters and numbers. No special characters.")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email format")
    private String email;

    private String password;
    private String confirmPassword;

    @NotBlank(message = "First name is required")
    @Size(min = 3, message = "First name is too short")
    @Size(max = 35, message = "First name is too long")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters")
    private String fName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, message = "Last name is too short")
    @Size(max = 35, message = "Last name is too long")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    private String lName;

    private String tagline;
    private String bio;
    private String profilePic;
    private boolean enabled = false;
    private boolean email_verified = false;
    private String whatIDo;
    private String twitterUrl;
    private String githubUrl;
    private String linkedInUrl;
    private String stackoverflowUrl;
    private String codePenUrl;
    private String company;
    private String website;
    private Long mobile;
    private String careerSummary;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private AuthProvider provider;
    private String providerId;
    private List<Role> roles = new ArrayList<>();

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
        this.roles = (List<Role>) user.getRoles();
    }
}
