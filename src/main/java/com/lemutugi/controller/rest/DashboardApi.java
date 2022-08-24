package com.lemutugi.controller.rest;

import com.lemutugi.payload.request.DashboardRequest;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.service.PrivilegeService;
import com.lemutugi.service.RoleService;
import com.lemutugi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;

@RequestMapping("/api/admin/*")
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN", "ROLE_EDITOR"})
@RestController
public class DashboardApi {
    private UserService userService;
    private RoleService roleService;
    private PrivilegeService privilegeService;

    @Autowired
    public DashboardApi(UserService userService, RoleService roleService, PrivilegeService privilegeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    @GetMapping(value = {"/dashboard", "/", "/home"})
    public ResponseEntity<ApiResponse> showDashboard() {
        DashboardRequest dashboardRequest = new DashboardRequest(userService.getTotalUsers(), roleService.getTotalRoles(), privilegeService.getTotalPrivileges());

        ApiResponse apiResponse = new ApiResponse(true, "Dashboard data fetched successfully");
        HashMap<String, Object> data = new HashMap<>();
        data.put("dashboardRequest", dashboardRequest);
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }
}
