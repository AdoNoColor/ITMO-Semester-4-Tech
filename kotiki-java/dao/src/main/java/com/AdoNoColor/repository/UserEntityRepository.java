package com.AdoNoColor.repository;

import com.AdoNoColor.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
