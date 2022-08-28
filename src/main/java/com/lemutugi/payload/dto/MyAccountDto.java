package com.lemutugi.payload.dto;

import com.lemutugi.model.User;
import com.lemutugi.validation.constraint.Name;
import com.lemutugi.validation.constraint.OptionalUrl;
import com.lemutugi.validation.constraint.PhoneNumber;
import com.lemutugi.validation.constraint.ShortDescription;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MyAccountDto {
    protected Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username should contain at least 3 characters")
    @Size(max = 35, message = "Username should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should contain only letters and numbers. No special characters.")
    protected String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email format")
    protected String email;

    @NotBlank(message = "First name is required")
    @Size(min = 3, message = "First name should contain at least 3 characters")
    @Size(max = 35, message = "First name should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "First name should contain only letters")
    protected String fName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, message = "Last name should contain at least 3 characters")
    @Size(max = 35, message = "Last name should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "Last name should contain only letters")
    protected String lName;

    @Name(message = "Tagline should contain at least 3 characters and should not exceed 50 characters")
    protected String tagline;

    @ShortDescription(message = "Bio should contain at least 50 characters and should not exceed 300 characters ")
    protected String bio;

    @ShortDescription(message = "What I do should contain at least 50 characters and should not exceed 300 characters ")
    protected String whatIDo;

    @OptionalUrl(message = "Please provide valid twitter url format")
    protected String twitterUrl;

    @OptionalUrl(message = "Please provide valid github url format")
    protected String githubUrl;

    @OptionalUrl(message = "Please provide valid linkedin url format")
    protected String linkedInUrl;

    @OptionalUrl(message = "Please provide valid stackoverflow url format")
    protected String stackoverflowUrl;

    @OptionalUrl(message = "Please provide valid codepen url format")
    protected String codePenUrl;

    @OptionalUrl(message = "Please provide valid website url format")
    protected String website;

    @Name(message = "Company name should contain at least 3 characters and should not exceed 50 characters")
    protected String company;

    @PhoneNumber(message = "Please provide a valid mobile number")
    protected Long mobile;

    @ShortDescription(message = "Career summary should contain at least 50 characters and should not exceed 300 characters ")
    protected String careerSummary;

    public MyAccountDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.fName = user.getFName();
        this.lName = user.getLName();
        this.tagline = user.getTagline();
        this.bio = user.getBio();
        this.whatIDo = user.getWhatIDo();
        this.twitterUrl = user.getTwitterUrl();
        this.githubUrl = user.getGithubUrl();
        this.linkedInUrl = user.getLinkedInUrl();
        this.stackoverflowUrl = user.getStackoverflowUrl();
        this.codePenUrl = user.getCodePenUrl();
        this.website = user.getWebsite();
        this.company = user.getCompany();
        this.mobile = user.getMobile();
        this.careerSummary = user.getCareerSummary();
    }
}
