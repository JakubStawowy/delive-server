package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.security.api.IAuthorizeService;
import com.example.rentiaserver.security.api.ITokenProvider;
import com.example.rentiaserver.security.to.LoginTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(LoginController.BASE_ENDPOINT)
public final class LoginController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL;
    private final UserRepository userRepository;
    private final ITokenProvider tokenProvider;
    private final IAuthorizeService authorizeService;

    @Autowired
    public LoginController(UserRepository userRepository, ITokenProvider tokenProvider, IAuthorizeService authorizeService) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authorizeService = authorizeService;
    }

    @PostMapping(value = EndpointConstants.LOGIN_ENDPOINT)
    public ResponseEntity<LoginTo> loginUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        UserPo user;
        if ((user = authorizeService.authorizeUserWithEmailAndPassword(email, password)) != null) {
            user.setLogged(true);
            userRepository.save(user);
            return new ResponseEntity<>(new LoginTo(
                    user.getId(),
                    tokenProvider.generateUserToken(user),
                    user.getRoles()
                    ), HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
