package com.example.rentiaserver.security.controller;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.base.exception.RegisterConflictException;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterControllerTest {

    @Mock
    private UserService userService;

    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerController = new RegisterController(userService);
    }

    @Test
    void shouldCallRegisterUser() throws EntityNotFoundException, RegisterConflictException {
        // TODO after implementing RegisterService
        UserPo testUser = new UserPo();
        registerController.registerUser(testUser);
    }

}