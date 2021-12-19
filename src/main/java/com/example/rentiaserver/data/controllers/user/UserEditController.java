package com.example.rentiaserver.data.controllers.user;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.data.to.UserTo;
import com.example.rentiaserver.security.api.IAuthorizeService;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import com.example.rentiaserver.security.to.ResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(UserEditController.BASE_ENDPOINT)
public final class UserEditController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/users";

    private final UserService userService;
    private final IAuthorizeService authorizeService;

    @Autowired
    public UserEditController(UserService userService, IAuthorizeService authorizeService) {
        this.userService = userService;
        this.authorizeService = authorizeService;
    }

    @PutMapping(value = "/edit")
    public ResponseTo editUser(@RequestBody UserTo userTo, HttpServletRequest request) {
        Long userId = JsonWebTokenHelper.getRequesterId(request);
        if (userTo.getOldPassword() != null && !authorizeService.authorizeUserWithIdAndPassword(userId, userTo.getOldPassword())) {
            return new ResponseTo(false, "Wrong old password value", HttpStatus.BAD_REQUEST);
        }
        return userService.findUserById(userId)
                .map(userPo -> editUser(userPo, userTo))
                .orElse(new ResponseTo(false, "User not found", HttpStatus.NOT_FOUND));
    }

    private ResponseTo editUser(UserPo user, UserTo userTo) {
        if (userTo.getNewPassword() != null) {
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(userTo.getNewPassword(), salt);
            user.setSalt(salt);
            user.setPassword(hashedPassword);
        }
        user.setName(userTo.getName());
        user.setSurname(userTo.getSurname());
        user.setPhone(userTo.getPhone());
        userService.saveUser(user);
        return new ResponseTo(true, "User edited successfully", HttpStatus.OK);
    }
}
