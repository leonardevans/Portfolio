package com.lemutugi.payload.dto;

import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.validation.constraint.OptionalUrl;
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
public class UserDto extends MyAccountDto{
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
        this.profilePic = user.getProfilePic();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fName = user.getFName();
        this.lName = user.getLName();
        this.tagline = user.getTagline();
        this.bio = user.getBio();
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
