package com.lemutugi.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemutugi.model.Privilege;
import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor()
public class UserPrincipal implements OAuth2User, UserDetails, Serializable {
    public static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private Boolean enabled;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    private String username;
    private boolean email_verified = false;
    private String fName;
    private String lName;
    private String tagline;
    private String bio;
    private String profilePic;
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

    public static UserPrincipal build(User user){
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) getAuthorities(user.getRoles());
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.getAccountNonExpired(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired(),
                authorities,
                null,
                user.getUsername(),
                user.isEmail_verified(),
                user.getFName(),
                user.getLName(),
                user.getTagline(),
                user.getBio(),
                user.getProfilePic(),
                user.getWhatIDo(),
                user.getTwitterUrl(),
                user.getGithubUrl(),
                user.getLinkedInUrl(),
                user.getStackoverflowUrl(),
                user.getCodePenUrl(),
                user.getCompany(),
                user.getWebsite(),
                user.getMobile(),
                user.getCareerSummary()
                );
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    public static UserPrincipal build(User user, Map<String, Object> attributes){
        UserPrincipal userPrincipal = UserPrincipal.build(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    private static List<String> getPrivileges(Collection<Role> roles) {
//        list of the names of all privileges and roles
        List<String> privileges = new ArrayList<>();

//        List of all the roles' privileges
        List<Privilege> allPrivileges = new ArrayList<>();

        for (Role role : roles) {
//            add the role's name to the privileges list
            privileges.add(role.getName());

//            add all of this role's privileges to our all privileges list
            allPrivileges.addAll(role.getPrivileges());
        }

        for (Privilege privilege : allPrivileges) {
            // add this privilege's name to our privileges list
            privileges.add(privilege.getName());
        }
        return privileges;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.fName + " " + this.lName;
    }
}
