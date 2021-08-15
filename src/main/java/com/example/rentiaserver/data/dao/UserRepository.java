package com.example.rentiaserver.data.dao;

import com.example.rentiaserver.data.po.UserPo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserPo, Long> {
    Optional<UserPo> getUserByEmailAndPassword(String email, String password);
    Optional<UserPo> getUserByEmail(String email);
}
