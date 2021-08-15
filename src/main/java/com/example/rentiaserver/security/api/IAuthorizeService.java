package com.example.rentiaserver.security.api;

import com.example.rentiaserver.data.po.UserPo;

public interface IAuthorizeService {
    boolean authorizeUserWithIdAndPassword(Long id, String password);
    UserPo authorizeUserWithEmailAndPassword(String email, String password);
}
