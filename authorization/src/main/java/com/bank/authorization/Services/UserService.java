package com.bank.authorization.Services;

import com.bank.authorization.DTO.UserDto;
import com.bank.authorization.Entities.Role;
import com.bank.authorization.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUser(UserDto userDto);

    Optional<User> getUserById(Long id); // Поиск пользователя по ID

    Optional<User> findByProfileId(Long profileId); // Поиск пользователя по имени

    List<User> getAllUsers(); // Получение всех пользователей

    User saveUser(User user); // Сохранение нового пользователя

    void deleteUser(Long id); // Удаление пользователя

    User updateUser(Long id, UserDto userDto,  Role role); // Обновление пользователя и ролей
}

