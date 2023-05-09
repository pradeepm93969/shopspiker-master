package com.shopspiker.auth.service;

import com.shopspiker.auth.jpa.entity.Role;
import com.shopspiker.auth.modal.RoleModal;

import java.util.List;

public interface RoleService {
    public RoleModal getById(String id);
    public List<RoleModal> getAll();
    public RoleModal create(RoleModal request);
    public RoleModal update(String id, RoleModal request);
    public void deleteById(String id);

    public Role findById(String id);
    public Role findByIdAndActive(String id);

}
