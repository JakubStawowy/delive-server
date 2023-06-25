package com.example.rentiaserver.user.model.bc;

public enum UserRoles {
    ROLE_ADMIN,

    ROLE_USER;

    public String getValueWithoutPrefix() {
        return this.toString().substring(this.toString().indexOf("_") + 1);
    }
}
