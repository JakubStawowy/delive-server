package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.finance.po.UserWalletPo;
import com.example.rentiaserver.security.to.ResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    @PostMapping(value = EndpointConstants.REGISTER_USER_ENDPOINT, consumes = "application/json")
    public ResponseTo registerUser(@RequestBody UserPo user) {
        return userService.getUserByEmail(user.getEmail()).map(userPo -> new ResponseTo(false, "User with this email already exists", HttpStatus.CONFLICT))
                .orElse(saveUser(user));
    }

    private ResponseTo saveUser(UserPo user) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        UserWalletPo userWalletPo = new UserWalletPo("EUR", new BigDecimal("0.0"));
        user.setUserWalletPo(userWalletPo);
        userService.saveUser(user);
        return new ResponseTo(true, "User registered successfully", HttpStatus.OK);
    }
}
