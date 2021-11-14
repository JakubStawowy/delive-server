package com.example.rentiaserver.data.services.user;

import com.example.rentiaserver.data.po.UserPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserDao extends CrudRepository<UserPo, Long> {
    Optional<UserPo> getUserByEmail(String email);
}
