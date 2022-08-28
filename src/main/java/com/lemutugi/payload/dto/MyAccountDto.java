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
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Username should contain at least 3 characters")
    @Size(max = 35, message = "Username should not exceed 35 characters")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should contain only letters and numbers. No special characters.")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email format")
    private String email;

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

    @ShortDescription(message = "What I do should contain at least 50 characters and should not exceed 300 characters ")
    private String whatIDo;

    @OptionalUrl(message = "Please provide valid twitter url format")
    private String twitterUrl;

    @OptionalUrl(message = "Please provide valid github url format")
    private String githubUrl;

    @OptionalUrl(message = "Please provide valid linkedin url format")
    private String linkedInUrl;

    @OptionalUrl(message = "Please provide valid stackoverflow url format")
    private String stackoverflowUrl;

    @OptionalUrl(message = "Please provide valid codepen url format")
    private String codePenUrl;

    @OptionalUrl(message = "Please provide valid website url format")
    private String website;

    @Name(message = "Company name should contain at least 3 characters and should not exceed 50 characters")
    private String company;

    @PhoneNumber(message = "Please provide a valid mobile number")
    private Long mobile;

    @ShortDescription(message = "Career summary should contain at least 50 characters and should not exceed 300 characters ")
    private String careerSummary;

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
