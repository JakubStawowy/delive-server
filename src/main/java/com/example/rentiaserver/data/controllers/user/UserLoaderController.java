package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.services.UserService;
import com.example.rentiaserver.data.to.UserTo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(UserLoaderController.BASE_ENDPOINT)
public final class UserLoaderController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/users";
    private final UserService userService;

    @Autowired
    public UserLoaderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({EndpointConstants.INDEX_ENDPOINT, EndpointConstants.INDEX_ENDPOINT_SLASH})
    public List<UserTo> getUsers(){

        return StreamSupport.stream(userService.findAllUsers().spliterator(), false)
                .map(UserTo::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/details")
    public ResponseEntity<UserTo> getUser(@RequestParam Long userId){
        return userService.findUserById(userId)
                .map(user -> new ResponseEntity<>(new UserTo(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
