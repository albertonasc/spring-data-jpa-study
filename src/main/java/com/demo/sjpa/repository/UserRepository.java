package com.demo.sjpa.repository;

import com.demo.sjpa.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findByName(String name, PageRequest pageRequest);

    Page<UserEntity> findByAgeGreaterThanEqual(Long age, PageRequest pageRequest);

    Page<UserEntity> findByNameAndAgeGreaterThanEqual(String name, Long age, PageRequest pageRequest);

}
