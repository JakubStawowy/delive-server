package com.example.rentiaserver.data.dao;

import com.example.rentiaserver.data.po.UserDetailsPo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetailsPo, Long> {
    Optional<UserDetailsPo> getByNickname(String nickname);
}
