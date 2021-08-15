package com.example.rentiaserver.security.controllers;

import com.example.rentiaserver.data.dao.UserDetailsRepository;
import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = RegisterValidationController.BASE_ENDPOINT)
public final class RegisterValidationController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/validate";
    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public RegisterValidationController(UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping("/email")
    public Boolean checkIfEmailExists(@RequestParam("email") String email) {
        return userRepository.getUserByEmail(email).isPresent();
    }

    @GetMapping("/nickname")
    public Boolean checkIfNicknameExists(@RequestParam("nickname") String nickname) {
        return userDetailsRepository.getByNickname(nickname).isPresent();
    }
}
