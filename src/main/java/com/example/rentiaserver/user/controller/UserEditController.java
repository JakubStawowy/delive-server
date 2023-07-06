package com.example.rentiaserver.user.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.user.service.UserService;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(UserEditController.BASE_ENDPOINT)
public final class UserEditController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/users";

    private final UserService userService;

    @Autowired
    public UserEditController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/edit")
    public void editUser(@RequestBody UserTo userData, HttpServletRequest request)
            throws EntityNotFoundException, AuthenticationException {

        Long userId = AuthenticationHelper.getUserId(request);
        userService.edit(userId, userData);

    }
}
