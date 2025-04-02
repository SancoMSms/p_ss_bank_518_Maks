package com.bank.authorization.DTO;

import com.bank.authorization.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    String name;
    String description;

    public static RoleDto fromRole(Role role) {
        return new RoleDto(role.name(), role.getDiscription());
    }
}
