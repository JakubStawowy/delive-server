package com.example.rentiaserver.security.controller;

import com.example.rentiaserver.base.exception.RegisterConflictException;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.user.service.UserService;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;


@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = RegisterController.BASE_ENDPOINT)
public final class RegisterController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX;

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public void registerUser(@RequestBody UserPo user) throws EntityNotFoundException, RegisterConflictException {

        // TODO RegisterService
        UserTo userTo = userService.getUserByEmail(user.getEmail(), false);

        if (userTo != null) {
            throw new RegisterConflictException("User with email: " + user.getEmail() + " already exists.");
        }

        userService.register(user);
    }
}
