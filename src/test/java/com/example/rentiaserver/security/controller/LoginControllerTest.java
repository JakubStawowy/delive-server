package com.example.rentiaserver.security.controller;

import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import com.example.rentiaserver.security.service.AuthorizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

class LoginControllerTest {

    @Mock
    private AuthorizeService authorizeService;

    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginController = new LoginController(authorizeService);
    }

    @Test
    void shouldCallAuthorizeUserWithEmailAndPassword() throws AuthenticationException, EntityNotFoundException {

        // Given
        String testEmail = "test_email";
        String testPassword = "test_password";

        // When
        loginController.loginUser(testEmail, testPassword);

        // Then
        verify(authorizeService).authorizeUserWithEmailAndPassword(testEmail, testPassword);
    }

    @Test
    void shouldCallAuthorizationHelper() {

        // Given
        HttpServletRequest testHttpServletRequest = new MockHttpServletRequest();
        Long testUserId = 1L;

        try (MockedStatic<AuthenticationHelper> authenticationMock = mockStatic(AuthenticationHelper.class)) {

            authenticationMock.when(() -> AuthenticationHelper.getUserId(testHttpServletRequest))
                    .thenReturn(testUserId);

            // When
            loginController.getLoggedUserId(testHttpServletRequest);

            // Then
            authenticationMock.verify(() -> AuthenticationHelper.getUserId(testHttpServletRequest));
        }
    }
}