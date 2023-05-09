package com.shopspiker.auth.service.impl;

import com.shopspiker.auth.jpa.entity.Role;
import com.shopspiker.auth.jpa.repository.RoleRepository;
import com.shopspiker.auth.modal.RoleModal;
import com.shopspiker.auth.modal.mapper.AuthMapper;
import com.shopspiker.auth.service.RoleService;
import com.shopspiker.common.web.exception.CustomApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private AuthMapper mapper;

    @Override
    @Cacheable(value = "Role", key = "#id")
    public RoleModal getById(String id) {
        return mapper.toRoleDto(findById(id));
    }

    @Override
    @Cacheable(value = "Role")
    public List<RoleModal> getAll() {
        return mapper.toRoleDto(repository.findAll());
    }

    @Override
    @CacheEvict(value = "Role", allEntries = true)
    public RoleModal create(RoleModal request) {
        if (repository.findById(request.getId()).isPresent())
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "ID_TAKEN", "Id Taken");

        Role role = mapper.toRoleEntity(request);
        role = repository.save(role);
        return mapper.toRoleDto(role);
    }

    @Override
    @CachePut(value = "Role", key = "#id")
    public RoleModal update(String id, RoleModal request) {

        Role role = findById(id);
        request.setId(id);
        mapper.updateRoleEntity(request, role);

        role = repository.save(role);
        return mapper.toRoleDto(role);
    }

    @Override
    @CacheEvict(value = "Role", key = "#id")
    public void deleteById(String id) {
        repository.delete(findById(id));
    }

    @Override
    public Role findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new CustomApplicationException(HttpStatus.NOT_FOUND, "ROLE_NOT_FOUND", "Role not found"));
    }

    @Override
    public Role findByIdAndActive(String id) {
        Role role = findById(id);
        if (!role.isActive()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "ROLE_NOT_ACTIVE", "Role not active");
        }
        return role;
    }

}
