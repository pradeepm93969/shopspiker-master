package com.shopspiker.auth.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import com.shopspiker.auth.modal.ClientModal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.service.ClientService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping("/auth/v1/clients")
@Tag(name = "Client Management")
@SecurityRequirement(name = "bearerAuth")
public class ClientController {

    @Autowired
    private ClientService clientService;
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SECURITY','ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public ClientModal getById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        return clientService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SECURITY','ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public Page<ClientModal> get(
            @And({@Spec(path = "ids", params = "ids", paramSeparator = ',' , spec = In.class),
                    @Spec(path = "secret", params = "secret", spec = LikeIgnoreCase.class),
                    @Spec(path = "authorities", params = "authorities", spec = LikeIgnoreCase.class),
                    @Spec(path = "authorizedGrantTypes", params = "authorizedGrantTypes", spec = LikeIgnoreCase.class),
                    @Spec(path = "createdAt", params = {"createdAtGt",
                            "createdAtLt"}, spec = Between.class)}) Specification<Client> spec,

            @Positive @RequestParam(defaultValue = "1") Integer pageNo,
            @Positive @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return clientService.get(spec, pageNo - 1, pageSize, sortBy, sortDirection);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public ClientModal create(@Valid @RequestBody ClientModal request) {
        return clientService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SECURITY','ROLE_SUPER_ADMIN')")
    public ClientModal update(@PathVariable("id") @NotBlank(message = "id is mandatory") String id,
                              @Valid @RequestBody ClientModal request) {
        return clientService.update(id, request);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public void deleteById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        clientService.deleteById(id);
    }

}
