package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.po.UserDetailsPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(UserManageController.BASE_ENDPOINT)
public final class UserManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/users";
    private final UserRepository userRepository;

    @Autowired
    public UserManageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping(path = EndpointConstants.EDIT_USER_ENDPOINT, consumes = "application/json")
    public ResponseEntity<?> editUser(@RequestBody UserDetailsPo details, @PathVariable("id") Long id) {
        Optional<UserPo> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            UserPo user = optionalUser.get();
            details.setId(user.getUserDetails().getId());
            user.setUserDetails(details);
            Logger.getGlobal().log(Level.INFO, details.getName());
            Logger.getGlobal().log(Level.INFO, user.getUserDetails().getName());
            userRepository.save(optionalUser.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
