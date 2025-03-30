package com.bank.authorization.DTO;

import com.bank.authorization.Entities.Role;
import com.bank.authorization.Entities.User;

public record UserResponse(
        Long id,
        Role role,
        String profileId) {

    public static UserResponse fromEntity(User user){
        return new UserResponse(user.getId(), user.getRole(),user.getProfileId());
    }
}
