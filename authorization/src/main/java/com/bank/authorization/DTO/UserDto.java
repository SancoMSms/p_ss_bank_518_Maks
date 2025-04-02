package com.bank.authorization.DTO;

import com.bank.authorization.Entities.Role;
import com.bank.authorization.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{

        Role role;
        Long profileId;
    String password;

    public static UserDto fromUser(User user) {
        return new UserDto(user.getRoles()
                , user.getProfileId()
                , user.getPassword());
    }
}
