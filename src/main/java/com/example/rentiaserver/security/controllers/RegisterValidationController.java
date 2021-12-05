package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = RegisterValidationController.BASE_ENDPOINT)
public final class RegisterValidationController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/validate";
    private final UserService userService;

    @Autowired
    public RegisterValidationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email")
    public Boolean checkIfEmailExists(@RequestParam("email") String email) {
        return userService.getUserByEmail(email).isPresent();
    }
}
