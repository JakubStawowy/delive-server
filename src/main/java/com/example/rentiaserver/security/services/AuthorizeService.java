package com.example.rentiaserver.security.services;

import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.data.services.user.UserService;
import com.example.rentiaserver.security.api.IAuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public final class AuthorizeService implements IAuthorizeService {

    private final UserService userService;

    @Autowired
    public AuthorizeService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean authorizeUserWithIdAndPassword(final Long id, final String password) {
        return userService.findUserById(id).map(user -> user.getPassword().equals(BCrypt.hashpw(password, user.getSalt()))).orElse(false);
    }

    @Override
    @Nullable
    public UserPo authorizeUserWithEmailAndPassword(final String email, final String password) {
        return userService.getUserByEmail(email)
                .filter(user -> BCrypt.hashpw(password, user.getSalt()).equals(user.getPassword())).orElse(null);
    }
}
