package com.lemutugi.controller.rest.admin;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.model.Role;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.service.RoleService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
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
            data.put("search", "");
        }

        List<Role> roles = rolePage.getContent();
        data.put("roles", roles);

        data.putAll(this.addPagingAttributes(data, pageSize, pageNo, rolePage.getTotalPages(), rolePage.getTotalElements(), sortField, sortDir));

        ApiResponse apiResponse = new ApiResponse(true, "Roles fetched successfully");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }
}
