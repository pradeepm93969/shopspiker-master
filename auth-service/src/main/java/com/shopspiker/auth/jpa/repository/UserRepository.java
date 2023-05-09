package com.shopspiker.auth.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.common.jpa.entity.Phone;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    public Optional<User> findByPhone(Phone phone);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByEmailOrPhone(String email, Phone phone);
}
