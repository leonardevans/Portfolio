package com.lemutugi.controller.rest.admin;

import com.lemutugi.controller.util.HttpUtil;
import com.lemutugi.model.Role;
import com.lemutugi.model.User;
import com.lemutugi.model.enums.AuthProvider;
import com.lemutugi.payload.dto.UserDto;
import com.lemutugi.payload.response.ApiResponse;
import com.lemutugi.service.UserService;
import com.lemutugi.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
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

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        bindingResult = this.validateCreateUserData(bindingResult, userService, userDto);

        ApiResponse apiResponse = null;

        if (bindingResult.hasErrors()) {
            apiResponse = new ApiResponse(false, "Failed to create user. Please provide correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Map<String, Object> data = new HashMap<>();
        User user = userService.createUser(userDto);
        data.put("user", user);

        apiResponse = new ApiResponse(true, "User created successfully.");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize("hasAuthority('EDIT_USER')")
    @PutMapping
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        bindingResult = this.validateUpdateUserData(bindingResult, userService, userDto);

        ApiResponse apiResponse = null;

        if (bindingResult.hasErrors()) {
            apiResponse = new ApiResponse(false, "Failed to update user. Please provide correct information.");
            apiResponse.setErrors(this.getErrors(bindingResult));
            return ResponseEntity.badRequest().body(apiResponse);
        }

        Map<String, Object> data = new HashMap<>();
        User user = userService.updateUser(userDto);
        data.put("user", user);

        apiResponse = new ApiResponse(true, "User updated successfully.");
        apiResponse.setData(data);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") Long id){
        ApiResponse apiResponse = null;
        if (userService.deleteUserById(id)){
            apiResponse = new ApiResponse(true, "User deleted successfully.");
            return ResponseEntity.ok().body(apiResponse);
        }
        apiResponse = new ApiResponse(false, "Failed to delete user!");
        return ResponseEntity.internalServerError().body(apiResponse);
    }
}
