package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.UserTo;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(UserLoaderController.BASE_ENDPOINT)
public final class UserLoaderController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/users";

    private final UserService userService;

    @Autowired
    public UserLoaderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details")
    public ResponseEntity<UserTo> getUserDetails(@RequestParam Long userId){
        return userService.findUserById(userId)
                .map(user -> new ResponseEntity<>(UserTo.createUserTo(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/details/loggedUser")
    public ResponseEntity<UserTo> getLoggedUserDetails(HttpServletRequest request){
        return userService.findUserById(JsonWebTokenHelper.getRequesterId(request))
                .map(user -> new ResponseEntity<>(UserTo.createLoggedUserTo(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
