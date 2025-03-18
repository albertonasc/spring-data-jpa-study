package com.demo.sjpa.service;

import com.demo.sjpa.controller.dto.CreateUserDto;
import com.demo.sjpa.controller.dto.UpdateUserDto;
import com.demo.sjpa.entity.UserEntity;
import com.demo.sjpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserDto dto) {

        var entity = new UserEntity();

        entity.setName(dto.name());
        entity.setAge(dto.age());
        entity.setCreatedAt(LocalDateTime.now());

        return userRepository.save(entity);
    }

    public Page<UserEntity> findAll(Integer page, Integer pageSize) {
        var pageRequest = PageRequest.of(page, pageSize);

        return userRepository.findAll(pageRequest);
    }

    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<UserEntity> updateById(Long userId, UpdateUserDto dto) {

        var user = userRepository.findById(userId);

        if (user.isPresent()) {

            updateFields(dto, user);

            userRepository.save(user.get());

        }

        return user;
    }

    private void updateFields(UpdateUserDto dto, Optional<UserEntity> user) {
        if(StringUtils.hasText(dto.name())) {
            user.get().setName(dto.name());
        }

        if(!isNull(dto.age())) {
            user.get().setAge(dto.age());
        }
    }

    public boolean deleteById(Long userId) {

        var exists = userRepository.existsById(userId);

        if(exists) {
            userRepository.deleteById(userId);
        }

        return exists;
    }
}
