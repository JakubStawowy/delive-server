package com.example.rentiaserver.security.api;

import com.example.rentiaserver.data.po.UserPo;

public interface ITokenProvider {
    String generateUserToken(UserPo user);
}
