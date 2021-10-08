package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = EndpointConstants.EDIT_USER_ENDPOINT)
    public ResponseEntity<?> editUser(@RequestBody UserTo userTo) {
        return userRepository.findById(userTo.getId())
                .map(userPo -> editUser(userPo, userTo))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<?> editUser(UserPo user, UserTo userTo) {
        // TODO
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
