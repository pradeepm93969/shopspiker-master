package com.shopspiker.auth.web.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shopspiker.auth.modal.RoleModal;
import com.shopspiker.auth.service.RoleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin/v1/roles")
@Tag(name = "Role Management")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    @Autowired
    private RoleService roleService;
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SECURITY','ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public RoleModal getById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        return roleService.getById(id);
    }
    
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SECURITY','ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public List<RoleModal> getAll() {
        return roleService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public RoleModal create(@Valid @RequestBody RoleModal request) {
        return roleService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public RoleModal update(@PathVariable("id") @NotBlank(message = "id is mandatory") String id,
                            @Valid @RequestBody RoleModal request) {
        return roleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public void deleteById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        roleService.deleteById(id);
    }

}
