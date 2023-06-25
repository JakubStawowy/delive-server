package com.example.rentiaserver.user.service;

import com.example.rentiaserver.user.model.mappers.UserMapper;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.user.api.IUserService;
import com.example.rentiaserver.user.repository.UserDao;
import com.example.rentiaserver.user.model.to.UserTo;
import com.example.rentiaserver.base.exception.AuthenticationException;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.security.service.AuthorizeService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ServiceLoader;

@Service
public class UserService implements IUserService {

    private final UserDao userRepository;

    public UserService(UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserTo getUserById(Long userId, boolean isLogged) throws EntityNotFoundException {
        return userRepository.findById(userId)
                .map(isLogged ? UserMapper::mapLoggedUserPoToTo : UserMapper::mapSimpleUserPoToTo)
                .orElseThrow(() -> new EntityNotFoundException(UserTo.class, userId));
    }

    @Override
    public void edit(Long userId, UserTo userData) throws EntityNotFoundException, AuthenticationException {

        UserPo user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserPo.class, userId));

        String oldPassword = userData.getOldPassword();
        if (oldPassword != null && !checkUserPasswordEquals(user, oldPassword)) {
            throw new AuthenticationException("Wrong old password value");
        }

        // Set new password
        String newPassword = userData.getNewPassword();
        if (newPassword != null) {
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(newPassword, salt);

            user.setSalt(salt);
            user.setPassword(hashedPassword);
        }

        // Set name
        user.setName(userData.getName());

        // Set surname
        user.setSurname(userData.getSurname());

        // Set phone
        user.setPhone(userData.getPhone());

        userRepository.save(user);
    }

    @Override
    public UserTo getUserByEmail(String email, boolean returnNull) throws EntityNotFoundException {
        UserTo user = userRepository.getUserByEmail(email)
                .map(UserMapper::mapUserPoToTo)
                .orElse(null);

        if (!returnNull && user == null) {
            throw new EntityNotFoundException(UserTo.class, email);
        }

        return user;
    }

    @Override
    public UserPo getUserPoByEmail(String email) throws EntityNotFoundException {
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(UserTo.class, email));
    }

    @Override
    public UserPo getUserPoById(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserTo.class, userId));
    }

    @Override
    public void register(UserPo user) {
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setSalt(salt);
        userRepository.save(user);
    }

    @Override
    public void save(UserPo user) {
        userRepository.save(user);
    }


    /**
     * Taken from @see com.example.rentiaserver.security.service.AuthorizeService
     * because of cycle.
     * <p>
     * TODO use provider for @see com.example.rentiaserver.security.api.IAuthorizeService
     * */
    private boolean checkUserPasswordEquals(UserPo user, String password) {
        return BCrypt.hashpw(password, user.getSalt()).equals(user.getPassword());
    }
}
