package com.bank.authorization.Services;

import com.bank.authorization.DTO.UserDto;
import com.bank.authorization.Entities.Role;
import com.bank.authorization.Entities.User;
import com.bank.authorization.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final  UserRepository userRepository;

@Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    }
@Transactional
    @Override
    public void createUser(UserDto userDto) {
        // Создаем нового пользователя
    User user = new User();
    user.setProfileId(userDto.getProfileId());
    user.setRoles(userDto.getRole());
    user.setPassword(userDto.getPassword());
        // Сохраняем пользователя в базе данных
        userRepository.save(user);
    }
    @Transactional
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    @Transactional
    @Override
    public Optional<User> findByProfileId(Long profileId) {
        return userRepository.findById(profileId);
    }
    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public User saveUser(User user) {
        System.out.println("Пароль пользователя при сохранении " + user.getPassword());
        return userRepository.save(user);
    }
    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    @Override
    public User updateUser(Long id, UserDto userDto,  Role role) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // Обновляем данные пользователя
        user.setProfileId(userDto.getProfileId());

        // Обновляем роль пользователя
        if (role != null) {
            user.setRoles(role);
        }

        // Сохраняем обновленного пользователя
        return userRepository.save(user);
    }

}
