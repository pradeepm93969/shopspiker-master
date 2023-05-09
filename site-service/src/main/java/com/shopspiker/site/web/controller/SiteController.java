package com.shopspiker.site.web.controller;

import com.shopspiker.site.jpa.entity.Site;
import com.shopspiker.site.modal.SiteModal;
import com.shopspiker.site.service.SiteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/site/v1/sites")
@Tag(name = "Site Management")
@SecurityRequirement(name = "bearerAuth")
public class SiteController {

    @Autowired
    private SiteService SiteService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SITE','ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public SiteModal getById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        return SiteService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SITE','ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public Page<SiteModal> get(
            @And({@Spec(path = "ids", params = "ids", paramSeparator = ',' , spec = In.class),
                    @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "createdAt", params = {"createdAtGt",
                            "createdAtLt"}, spec = Between.class)}) Specification<Site> spec,

            @Positive @RequestParam(defaultValue = "1") Integer pageNo,
            @Positive @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return SiteService.get(spec, pageNo - 1, pageSize, sortBy, sortDirection);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public SiteModal create(@Valid @RequestBody SiteModal request) {
        return SiteService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public SiteModal update(@PathVariable("id") @NotBlank(message = "id is mandatory") String id,
                              @Valid @RequestBody SiteModal request) {
        return SiteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public void deleteById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        SiteService.deleteById(id);
    }

}
