package com.example.rentiaserver.user.controller;

import com.example.rentiaserver.user.service.UserService;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(UserLoaderController.BASE_ENDPOINT)
public final class UserLoaderController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/users";

    private final UserService userService;

    @Autowired
    public UserLoaderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public UserTo getUserDetails(@RequestParam Long userId) throws EntityNotFoundException {
        return userService.getUserById(userId);
    }

    @GetMapping("/details/loggedUser")
    public UserTo getLoggedUserDetails(HttpServletRequest request) throws EntityNotFoundException {
        Long userId = AuthenticationHelper.getUserId(request);
        return userService.getUserById(userId, true);
    }
}
