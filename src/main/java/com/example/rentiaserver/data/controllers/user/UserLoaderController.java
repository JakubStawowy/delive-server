package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.to.UserTo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(UserLoaderController.BASE_ENDPOINT)
public final class UserLoaderController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/users";
    private final UserRepository userRepository;

    @Autowired
    public UserLoaderController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping({EndpointConstants.INDEX_ENDPOINT, EndpointConstants.INDEX_ENDPOINT_SLASH})
    public List<UserTo> getUsers(){

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserTo::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/details")
    public ResponseEntity<UserTo> getUser(@RequestParam Long userId){
        return userRepository.findById(userId)
                .map(user -> new ResponseEntity<>(new UserTo(user), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
