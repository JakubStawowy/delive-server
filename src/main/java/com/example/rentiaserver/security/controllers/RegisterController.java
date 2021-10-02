package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.security.to.ResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = RegisterController.BASE_ENDPOINT)
public final class RegisterController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL;
    private final UserRepository userRepository;

    @Autowired
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = EndpointConstants.REGISTER_USER_ENDPOINT, consumes = "application/json")
    public ResponseTo registerUser(@RequestBody UserPo user) {
        return userRepository.getUserByEmail(user.getEmail()).map(userPo -> new ResponseTo(false, "User with this email already exists", HttpStatus.CONFLICT))
                .orElse(saveUser(user));
    }

    private ResponseTo saveUser(UserPo user) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        userRepository.save(user);
        return new ResponseTo(true, "User registered successfully", HttpStatus.OK);
    }
}
