package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = LogoutController.BASE_ENDPOINT)
public final class LogoutController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX;
    private final UserService userService;

    @Autowired
    public LogoutController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        Optional<UserPo> optionalUserPo = userService.findUserById(JsonWebTokenHelper.getRequesterId(request));
        return optionalUserPo.map(this::saveUser).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<?> saveUser(UserPo user) {
        user.setLogged(false);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
