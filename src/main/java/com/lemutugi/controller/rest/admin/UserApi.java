package com.lemutugi.controller.rest.admin;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.service.UserService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users/")
@RolesAllowed({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
public class UserApi extends HttpUtil {
    private UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = Constants.SMALL_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = "fName", required = false) String sortField,
            @RequestParam(defaultValue = "asc", required = false) String sortDir ,
            @RequestParam(required = false) String search
            ){
        Page<User> userPage = Page.empty();
        Map<String, Object> data = new HashMap<>();

        if (search != null){
            userPage = userService.searchUserByAllFields(pageNo, pageSize, sortField, sortDir, search);
            data.put("search", search);
        }else{
            userPage = userService.getAll(pageNo, pageSize, sortField, sortDir);
        }

        List<User> users = userPage.getContent();
        data.put("users", users);

        this.addPagingAttributes(data, pageSize, pageNo, userPage.getTotalPages(), userPage.getTotalElements(), sortField, sortDir);

        ApiResponse apiResponse = new ApiResponse(true, "Users fetched successfully");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("{id}")
    ResponseEntity<ApiResponse> getUser(@PathVariable("id") Long id){
        User user = userService.getUserById(id);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        ApiResponse apiResponse = new ApiResponse(true, "User fetched successfully.");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }
}
