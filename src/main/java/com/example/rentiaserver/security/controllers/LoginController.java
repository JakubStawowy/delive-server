package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.security.api.IAuthorizeService;
import com.example.rentiaserver.security.api.ITokenProvider;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import com.example.rentiaserver.security.to.LoginResponseTo;
import com.example.rentiaserver.security.to.ResponseTo;
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

    private final UserService userService;

    private final ITokenProvider tokenProvider;

    private final IAuthorizeService authorizeService;

    @Autowired
    public LoginController(UserService userService,
                           ITokenProvider tokenProvider,
                           IAuthorizeService authorizeService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authorizeService = authorizeService;
    }

    @PostMapping(value = "/login")
    public ResponseTo loginUser(@RequestParam String email, @RequestParam String password) {
        UserPo user = authorizeService.authorizeUserWithEmailAndPassword(email, password);
        if (user != null) {
            user.setLogged(true);
            userService.saveUser(user);
            return new LoginResponseTo(true,
                    HttpStatus.OK,
                    user.getId(),
                    tokenProvider.generateUserToken(user),
                    user.getRole(),
                    true);
        }
        return new ResponseTo(false, "Wrong email or password", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/userId")
    public Long getLoggedUserId(HttpServletRequest request) {
        return JsonWebTokenHelper.getRequesterId(request);
    }

}
