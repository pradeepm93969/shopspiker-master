package com.shopspiker.site.web.controller;

import com.shopspiker.site.jpa.entity.TaxRate;
import com.shopspiker.site.modal.TaxRateModal;
import com.shopspiker.site.service.TaxRateService;
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
@RequestMapping("/site/v1/taxRates")
@Tag(name = "TaxRate Management")
@SecurityRequirement(name = "bearerAuth")
public class TaxRateController {

    @Autowired
    private TaxRateService TaxRateService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SITE','ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public TaxRateModal getById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        return TaxRateService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_READ_SITE','ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public Page<TaxRateModal> get(
            @And({@Spec(path = "ids", params = "ids", paramSeparator = ',' , spec = In.class),
                    @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "createdAt", params = {"createdAtGt",
                            "createdAtLt"}, spec = Between.class)}) Specification<TaxRate> spec,

            @Positive @RequestParam(defaultValue = "1") Integer pageNo,
            @Positive @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return TaxRateService.get(spec, pageNo - 1, pageSize, sortBy, sortDirection);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public TaxRateModal create(@Valid @RequestBody TaxRateModal request) {
        return TaxRateService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGE_SITE','ROLE_SUPER_ADMIN')")
    public TaxRateModal update(@PathVariable("id") @NotBlank(message = "id is mandatory") String id,
                              @Valid @RequestBody TaxRateModal request) {
        return TaxRateService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public void deleteById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
        TaxRateService.deleteById(id);
    }

}
