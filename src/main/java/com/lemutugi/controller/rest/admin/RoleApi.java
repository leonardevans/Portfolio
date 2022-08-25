package com.lemutugi.controller.rest.admin;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.model.Role;
import com.lemutugi.payload.request.RoleRequest;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.service.RoleService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/roles/")
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
public class RoleApi extends HttpUtil {
    private RoleService roleService;

    @Autowired
    public RoleApi(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getRoles(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = "name", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            @RequestParam(required = false) String search
    ){
        Page<Role> rolePage = Page.empty();
        Map<String, Object> data = new HashMap<>();

        if (search != null){
            rolePage = roleService.search(pageNo, pageSize, sortField, sortDir, search);
            data.put("search", search);
        }else{
            rolePage = roleService.getAllRoles(pageNo, pageSize, sortField, sortDir);
        }

        List<Role> roles = rolePage.getContent();
        data.put("roles", roles);

        data.putAll(this.addPagingAttributes(data, pageSize, pageNo, rolePage.getTotalPages(), rolePage.getTotalElements(), sortField, sortDir));

        ApiResponse apiResponse = new ApiResponse(true, "Roles fetched successfully");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("{id}")
    ResponseEntity<ApiResponse> getRole(@PathVariable("id") Long id){
        Role role = roleService.getRoleById(id);
        Map<String, Object> data = new HashMap<>();
        data.put("role", role);
        ApiResponse apiResponse = new ApiResponse(true, "Role fetched successfully.");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable("id") Long id){
        ApiResponse apiResponse = null;
        if (roleService.deleteRoleById(id)){
            apiResponse = new ApiResponse(true, "Role deleted successfully.");
            return ResponseEntity.ok().body(apiResponse);
        }
        apiResponse = new ApiResponse(false, "Failed to delete role!");
        return ResponseEntity.internalServerError().body(apiResponse);
    }

    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping
    public ResponseEntity<ApiResponse> createRole(@Valid @RequestBody RoleRequest roleRequest, BindingResult bindingResult){
        bindingResult = this.validateCreateRoleData(bindingResult, roleService, roleRequest);

        ApiResponse apiResponse = null;

        if (bindingResult.hasErrors()) {
            apiResponse = new ApiResponse(false, "Failed to create role. Please provide correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Map<String, Object> data = new HashMap<>();
        Role role = roleService.createRole(roleRequest);
        data.put("role", role);

        apiResponse = new ApiResponse(true, "Role created successfully.");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize("hasAuthority('EDIT_ROLE')")
    @PutMapping
    public ResponseEntity<ApiResponse> updateRole(@Valid @RequestBody RoleRequest roleRequest, BindingResult bindingResult){
        bindingResult = this.validateUpdateRoleData(bindingResult, roleService, roleRequest);

        ApiResponse apiResponse = null;

        if (bindingResult.hasErrors()) {
            apiResponse = new ApiResponse(false, "Failed to update role. Please provide correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Map<String, Object> data = new HashMap<>();
        Role role = roleService.updateRole(roleRequest);
        data.put("role", role);

        apiResponse = new ApiResponse(true, "Role updated successfully.");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }
}
