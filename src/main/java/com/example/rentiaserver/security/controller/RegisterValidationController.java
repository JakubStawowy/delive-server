package com.example.rentiaserver.security.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
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
    public boolean checkIfEmailExists(@RequestParam("email") String email) {
        try {
            UserTo user = userService.getUserByEmail(email, true);

            return user != null;

        } catch (EntityNotFoundException ignored) {
            // This should never happen
            return false;
        }
    }
}
