package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = LogoutController.BASE_ENDPOINT)
public final class LogoutController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL;
    private final UserRepository userRepository;

    @Autowired
    public LogoutController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping(value = EndpointConstants.LOGOUT_ENDPOINT)
    public ResponseEntity<?> logout(@RequestParam("id") Long id) {
        Optional<UserPo> optionalUserPo = userRepository.findById(id);
        return optionalUserPo.map(this::saveUser).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<?> saveUser(UserPo user) {
        user.setLogged(false);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
