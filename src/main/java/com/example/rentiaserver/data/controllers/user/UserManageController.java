package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.services.user.UserService;
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
    private final UserService userService;

    @Autowired
    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(path = EndpointConstants.EDIT_USER_ENDPOINT)
    public ResponseEntity<?> editUser(@RequestBody UserTo userTo) {
        return userService.findUserById(userTo.getId())
                .map(userPo -> editUser(userPo, userTo))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private ResponseEntity<?> editUser(UserPo user, UserTo userTo) {
        // TODO
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
