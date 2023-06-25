package com.example.rentiaserver.user.api;

import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;

public interface IUserService {

    default UserTo getUserById(Long userId) throws EntityNotFoundException {
        return getUserById(userId, false);
    }

    UserTo getUserById(Long userId, boolean isLogged) throws EntityNotFoundException;

    void edit(Long userId, UserTo userData) throws EntityNotFoundException, AuthenticationException;

    UserTo getUserByEmail(String email, boolean returnNull) throws EntityNotFoundException;

    default UserTo getUserByEmail(String email) throws EntityNotFoundException {
        return getUserByEmail(email, false);
    }

    UserPo getUserPoByEmail(String email) throws EntityNotFoundException;

    UserPo getUserPoById(Long userId) throws EntityNotFoundException;

    void register(UserPo user);

    void save(UserPo user);
}
