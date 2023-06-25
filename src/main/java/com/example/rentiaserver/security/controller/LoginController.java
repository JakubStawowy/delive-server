package com.example.rentiaserver.security.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.security.api.IAuthorizeService;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import com.example.rentiaserver.security.jwt.JsonWebTokenGeneratorHelper;
import com.example.rentiaserver.security.model.to.LoginResponseTo;
import com.example.rentiaserver.base.model.to.ResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(LoginController.BASE_ENDPOINT)
public final class LoginController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX;

    private final IAuthorizeService authorizeService;

    @Autowired
    public LoginController(IAuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @PostMapping(value = "/login")
    public ResponseTo loginUser(@RequestParam String email, @RequestParam String password)
            throws EntityNotFoundException {

        try {
            UserTo user = authorizeService.authorizeUserWithEmailAndPassword(email, password);
            return LoginResponseTo.builder()
                    .operationSuccess(true)
                    .status(HttpStatus.OK)
                    .userId(user.getId())
                    .token(JsonWebTokenGeneratorHelper.generateUserToken(user))
                    .role(user.getRole())
                    .build();

        } catch (AuthenticationException ex) {
            return ResponseTo.builder()
                    .operationSuccess(false)
                    .status(HttpStatus.UNAUTHORIZED)
                    .message(ex.getMessage())
                    .build();
        }
    }

    @GetMapping(value = "/userId")
    public Long getLoggedUserId(HttpServletRequest request) {
        return AuthenticationHelper.getUserId(request);
    }

}
