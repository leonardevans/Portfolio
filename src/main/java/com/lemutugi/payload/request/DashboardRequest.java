package com.lemutugi.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardRequest {
    private Long totalUsers;
    private Long totalRoles;
    private Long totalPrivileges;
}