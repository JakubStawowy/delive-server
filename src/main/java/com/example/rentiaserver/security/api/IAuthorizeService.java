package com.example.rentiaserver.security.api;

import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;

public interface IAuthorizeService {

    boolean checkIfUserPasswordMatch(Long id, String password) throws EntityNotFoundException;

    UserTo authorizeUserWithEmailAndPassword(String email, String password) throws EntityNotFoundException, AuthenticationException;
}
