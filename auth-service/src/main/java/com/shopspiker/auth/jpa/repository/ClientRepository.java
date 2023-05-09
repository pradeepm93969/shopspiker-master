package com.shopspiker.auth.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shopspiker.auth.jpa.entity.Client;

public interface ClientRepository extends JpaRepository<Client, String>, JpaSpecificationExecutor<Client> {

    Optional<Client> findByIdAndSecretAndActive(String id, String secret, boolean isActive);

}
