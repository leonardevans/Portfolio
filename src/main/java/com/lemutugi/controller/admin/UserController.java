package com.lemutugi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@RequestMapping("/admin/users/")
@RolesAllowed("ROLE_ADMIN")
@Controller
public class UserController {
}
