package com.demo.sjpa.controller;

import com.demo.sjpa.controller.dto.ApiResponse;
import com.demo.sjpa.controller.dto.CreateUserDto;
import com.demo.sjpa.controller.dto.PaginationResponse;
import com.demo.sjpa.controller.dto.UpdateUserDto;
import com.demo.sjpa.entity.UserEntity;
import com.demo.sjpa.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto dto) {

        var user = userService.createUser(dto);

        return ResponseEntity.created(URI.create("/users/" + user.getUserId())).build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserEntity>> listAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(name = "pagaSize", defaultValue = "5") Integer pageSize) {

        var pageResponse = userService.findAll(page, pageSize);

        return ResponseEntity.ok(new ApiResponse<>(
                pageResponse.getContent(),
                new PaginationResponse(pageResponse.getNumber(), pageResponse.getSize(), pageResponse.getTotalElements(), pageResponse.getTotalPages())
        ));
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserEntity> findById(@PathVariable("userId") Long userId) {

        var user = userService.findById(userId);

        return  user.isPresent() ?
                 ResponseEntity.ok(user.get()) :
                 ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") Long userId,
                                           @RequestBody UpdateUserDto dto) {

        var user = userService.updateById(userId, dto);

        return  user.isPresent() ?
                 ResponseEntity.noContent().build() :
                 ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") Long userId) {

        var userDeleted = userService.deleteById(userId);

        return  userDeleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }



}
