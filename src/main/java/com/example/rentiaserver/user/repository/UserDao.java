package com.example.rentiaserver.user.repository;

import com.example.rentiaserver.user.model.po.UserPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<UserPo, Long> {
    Optional<UserPo> getUserByEmail(String email);
}
