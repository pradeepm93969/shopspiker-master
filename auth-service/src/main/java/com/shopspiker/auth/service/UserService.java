package com.shopspiker.auth.service;

import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.modal.UserModal;
import com.shopspiker.auth.modal.request.ChangePasswordRequest;
import com.shopspiker.common.jpa.entity.Phone;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {

    public UserModal getById(String id);
    public Page<UserModal> get(Specification<User> spec, Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    public List<User> get(Specification<User> spec, String sortBy, String sortDirection);
    public UserModal create(UserModal request);
    public UserModal update(String id, UserModal request);
    public void deleteById(String id);
    void changePassword(String name, ChangePasswordRequest request);


    public User findById(String id);
    public User findByIdAndActive(String id);
    public User findByEmailOrPhoneAndActive(String email, Phone phone);

    public void save(User user);

}
