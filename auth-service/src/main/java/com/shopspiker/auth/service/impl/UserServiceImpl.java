package com.shopspiker.auth.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.shopspiker.auth.modal.UserModal;
import com.shopspiker.auth.modal.mapper.AuthMapper;
import com.shopspiker.auth.modal.request.ChangePasswordRequest;
import com.shopspiker.auth.service.RoleService;
import com.shopspiker.auth.service.validator.UserValidator;
import com.shopspiker.common.web.exception.CustomApplicationException;
import com.shopspiker.common.service.util.ApplicationUtil;
import com.shopspiker.common.service.validation.PhoneValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.jpa.repository.UserRepository;
import com.shopspiker.auth.service.UserService;
import com.shopspiker.common.jpa.entity.Phone;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthMapper mapper;

    @Cacheable(value = "User", key = "#id")
    public UserModal getById(String id) {
        return mapper.toUserDto(findById(id));
    }

    public Page<UserModal> get(Specification<User> spec, Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                Sort.by((StringUtils.isBlank(sortDirection) || sortDirection.equalsIgnoreCase("asc"))
                        ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));

        Page<User> users = userRepository.findAll(spec, pageable);
        return new PageImpl<>(mapper.toUserDto(users.getContent()), pageable,
                users.getTotalElements());
    }

    public List<User> get(Specification<User> spec, String sortBy, String sortDirection) {
        Sort sort = Sort.by((StringUtils.isBlank(sortDirection) || sortDirection.equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        return userRepository.findAll(spec, sort);
    }

    public UserModal create(UserModal request) {

        PhoneValidator.validate(request.getPhone());
        userValidator.isPhoneNumberAvailable(request.getPhone());
        userValidator.isEmailAvailable(request.getEmail());

        // register the user
        User user = mapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLastSuccessfulLoginAt(OffsetDateTime.now());
        user.setActive(true);

        if (request.getRoles() != null && !request.getRoles().isEmpty())
            user.setRoles(new ArrayList<>(request.getRoles().stream().map(
                    s -> roleService.findByIdAndActive(s.getId()))
                    .collect(Collectors.toSet())));
        return mapper.toUserDto(userRepository.save(user));
    }

    @CachePut(value = "User", key = "#id")
    public UserModal update(String id, UserModal request) {

        User user = findById(id);
        if (!request.getPhone().equals(user.getPhone())) {
            PhoneValidator.validate(request.getPhone());
            userValidator.isPhoneNumberAvailable(request.getPhone());
        }
        if (!request.getEmail().equals(user.getEmail())) {
            userValidator.isEmailAvailable(request.getEmail());
        }

        mapper.updateUserEntity(request, user);
        if (request.getRoles() != null && !request.getRoles().isEmpty())
            user.setRoles(new ArrayList<>(request.getRoles().stream().map(
                            s -> roleService.findByIdAndActive(s.getId()))
                    .collect(Collectors.toSet())));
        return mapper.toUserDto(userRepository.save(user));
    }

    @Override
    @CacheEvict(value = "User", key = "#id")
    public void deleteById(String id) {
        userRepository.delete(findById(id));
    }

    @Override
    public void changePassword(String id, ChangePasswordRequest request) {
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        save(user);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new CustomApplicationException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User not found"));
    }

    @Override
    public User findByIdAndActive(String id) {
        User user = findById(id);
        if (!user.isActive()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "USER_NOT_ACTIVE", "User not active");
        }
        return user;
    }

    @Override
    public User findByEmailOrPhoneAndActive(String email, Phone phone) {
        User user = userRepository.findByEmailOrPhone(email, phone).orElseThrow(
                () -> new CustomApplicationException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User not found"));
        if (!user.isActive()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "USER_NOT_ACTIVE", "User not active");
        }
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}
