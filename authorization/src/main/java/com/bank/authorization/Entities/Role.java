package com.bank.authorization.Entities;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role  {
    ADMIN("Администратор"),
    USER("Пользователь");

    // Метод для получения описания роли
    private final String discription;

    Role(String discription) {
        this.discription = discription;
    }


}
