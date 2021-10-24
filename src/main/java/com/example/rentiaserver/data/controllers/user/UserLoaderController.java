package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.services.UserService;
import com.example.rentiaserver.data.to.UserTo;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.rentiaserver.constants.ApplicationConstants.Security.ID_PREFIX;

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

    @GetMapping("/details/loggedUser/id")
    public Long getLoggedUserId(HttpServletRequest request) {
        return JsonWebTokenHelper.getRequesterId(request);
    }
}
