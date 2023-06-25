package com.example.rentiaserver.security.service;

import com.example.rentiaserver.user.model.mappers.UserMapper;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.service.UserService;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.security.api.IAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class AuthorizeService implements IAuthorizeService {

    private final UserService userService;

    @Autowired
    public AuthorizeService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean checkIfUserPasswordMatch(final Long userId, final String password) throws EntityNotFoundException {

        UserPo user = userService.getUserPoById(userId);

        return checkUserPasswordEquals(user, password);
    }

    @Override
    public UserTo authorizeUserWithEmailAndPassword(final String email, final String password)
            throws EntityNotFoundException, AuthenticationException {

        UserPo userPo = userService.getUserPoByEmail(email);

        return Optional.of(userPo)
                .filter(user -> checkUserPasswordEquals(user, password))
                .map(UserMapper::mapLoggedUserPoToTo)
                .orElseThrow(() -> new AuthenticationException("Wrong email or password"));
    }

    private boolean checkUserPasswordEquals(UserPo user, String password) {
        return BCrypt.hashpw(password, user.getSalt()).equals(user.getPassword());
    }
}
