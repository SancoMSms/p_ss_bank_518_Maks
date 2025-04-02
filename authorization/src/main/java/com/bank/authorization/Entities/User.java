package com.bank.authorization.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@NoArgsConstructor  // Конструктор без параметров
@AllArgsConstructor  // Конструктор со всеми полями
@Setter
@Getter
@Entity
@Table(name = "user")
public class User implements UserDetails  {



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id") // Этот столбец будет ключом
    private Long id;

    @Enumerated(EnumType.STRING) // Сохраняем как строку в БД
    @Column(name = "role")
    private Role roles;

    @NotNull
    @Column(name = "profile_id")
    private Long  profileId;

    @NotNull
    @Column(name = "password")
    private String password;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return "Нет имени у пользователя ";
    }

}
