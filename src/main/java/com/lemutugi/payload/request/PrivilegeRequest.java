package com.lemutugi.payload.request;

import com.lemutugi.model.Privilege;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PrivilegeRequest {
    @Size(min = 3, message = "Privilege's name should be at least 3 characters")
    private String name;

    private Long id;

    public PrivilegeRequest(Privilege privilege) {
        this.name = privilege.getName();
        this.id = privilege.getId();
    }
}
