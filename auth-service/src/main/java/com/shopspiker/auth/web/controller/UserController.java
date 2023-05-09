package com.shopspiker.auth.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.shopspiker.auth.modal.request.ChangePasswordRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import com.shopspiker.auth.modal.UserModal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.service.UserService;
import com.shopspiker.common.service.CsvService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;


@RestController
@Tag(name = "User Management")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private CsvService csvService;

	@GetMapping("/api/v1/users/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGE_USERS','ROLE_READ_USERS','ROLE_SUPER_ADMIN')" +
			"or #userId == authentication.principal.username")
	public UserModal getById(@PathVariable("id")
								@NotBlank(message = "id is mandatory") String id) {
		return service.getById(id);
	}

	@GetMapping("/api/v1/users/changePassword")
	@PreAuthorize("#userId == authentication.principal.username")
	public void changePassword(Principal principal,
							   @Valid @RequestBody ChangePasswordRequest request) {
		service.changePassword(principal.getName(), request);
	}
	
	@GetMapping("/admin/v1/users")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGE_USERS','ROLE_READ_USERS','ROLE_SUPER_ADMIN')")
	public Page<UserModal> get(
			@And({
                @Spec(path = "ids", params = "ids", paramSeparator = ',', spec = In.class),
                @Spec(path = "email", params = "email", spec = LikeIgnoreCase.class),
                @Spec(path = "phone", params = "phone", spec = LikeIgnoreCase.class),
                @Spec(path = "active", params = "active", spec = Equal.class),
                @Spec(path = "firstName", params = "firstName", spec = LikeIgnoreCase.class),
                @Spec(path = "lastName", params = "lastName", spec = LikeIgnoreCase.class)
			}) Specification<User> spec,
			
			@Positive @RequestParam(defaultValue = "1") Integer pageNo, 
			@Positive @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
		return service.get(spec, pageNo-1, pageSize, sortBy, sortDirection);
	}
	
	@GetMapping("/admin/v1/users/download")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGE_USERS','ROLE_READ_USERS','ROLE_SUPER_ADMIN')")
	public ResponseEntity<Resource> extract(
			@And({
				@Spec(path = "id", params = "id", paramSeparator = ',', spec = In.class),
                @Spec(path = "email", params = "email", spec = LikeIgnoreCase.class),
                @Spec(path = "phone", params = "phone", spec = LikeIgnoreCase.class),
                @Spec(path = "enabled", params = "enabled", spec = Equal.class),
                @Spec(path = "firstName", params = "firstName", spec = LikeIgnoreCase.class),
                @Spec(path = "lastName", params = "lastName", spec = LikeIgnoreCase.class)
			}) Specification<User> spec,
			
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) throws IOException {
		List<User> user = service.get(spec, sortBy, sortDirection);
		
		List<String> noNeededColumn = Arrays.asList(new String[] {"createdAt", "createdBy", "updatedAt", "updatedBy", "roles", "roleInput", "password"});
		Resource resource = csvService.generateCsvFile(user, noNeededColumn);

        LocalDateTime now = LocalDateTime.now();
        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType("application/octet-stream"))
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"User_" + now + ".csv\"")
                             .body(resource);
	}
	
	@PostMapping("/admin/v1/users")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGE_USERS','ROLE_SUPER_ADMIN')")
	public UserModal create(@Valid @RequestBody UserModal request) {
		return service.create(request);
	}
	
	@PutMapping("/admin/v1/users/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_MANAGE_USERS','ROLE_SUPER_ADMIN')")
	public UserModal update(@PathVariable("id") @NotBlank(message = "id is mandatory") String id,
                            @Valid @RequestBody UserModal request) {
		return service.update(id, request);
	}

	@DeleteMapping("/admin/v1/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
	public void deleteById(@PathVariable("id") @NotBlank(message = "id is mandatory") String id) {
		service.deleteById(id);
	}

}
	
	