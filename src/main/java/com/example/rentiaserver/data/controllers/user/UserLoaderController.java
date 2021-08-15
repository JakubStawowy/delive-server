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
        List<UserTo> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(new UserTo(user)));
        return users;
    }

    @GetMapping(EndpointConstants.USER_ENDPOINT)
    public ResponseEntity<UserTo> getUser(@PathVariable("id") Long id){
        Optional<UserPo> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> new ResponseEntity<>(new UserTo(user), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
