package com.lemutugi.controller.rest;

import com.lemutugi.model.Privilege;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.service.PrivilegeService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/privileges/")
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
public class PrivilegeApi {
    private PrivilegeService privilegeService;

    @Autowired
    public PrivilegeApi(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping(value = {"/list", "/"})
    public ResponseEntity<ApiResponse> showPrivileges(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = "name", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            @RequestParam(required = false) String search
            ){
        Page<Privilege> privilegePage = Page.empty();
        Map<String, Object> data = new HashMap<>();

        if (search != null){
            privilegePage = privilegeService.search(pageNo, pageSize, sortField, sortDir, search);
            data.put("search", search);
        }else{
            privilegePage = privilegeService.getAllPrivileges(pageNo, pageSize, sortField, sortDir);
            data.put("search", "");
        }
        List<Privilege> privileges = privilegePage.getContent();
        data.put("privileges", privileges);

        data.put("pageSize", pageSize);
        data.put("currentPage", pageNo);
        data.put("totalPages", privilegePage.getTotalPages());
        data.put("totalItems", privilegePage.getTotalElements());

        data.put("sortField", sortField);
        data.put("sortDir", sortDir);

        ApiResponse apiResponse = new ApiResponse(true, "Privileges fetched successfully");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }
}
