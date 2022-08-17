package com.lemutugi.payload.request;

import com.lemutugi.model.Privilege;
import com.lemutugi.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleRequest {
    @NotBlank(message = "Role name is required")
    @Size(min = 3, message = "Role name should be at least 3 characters")
    private String name;

    private Long id;

    private List<Privilege> privileges = new ArrayList<>();

    public RoleRequest(Role role) {
        this.name = role.getName();
        this.id = role.getId();
        this.privileges = (List<Privilege>) role.getPrivileges();
    }
}
