package com.shopspiker.auth.modal.mapper;

import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.jpa.entity.Role;
import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.modal.ClientModal;
import com.shopspiker.auth.modal.RoleModal;
import com.shopspiker.auth.modal.UserModal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    ClientModal toClientDto(Client request);

    List<ClientModal> toClientDto(List<Client> list);

    @Mapping(target = "auditSection", ignore = true)
    Client toClientEntity(ClientModal request);

    @Mapping(target = "auditSection", ignore = true)
    Client updateClientEntity(ClientModal request, @MappingTarget Client client);


    RoleModal toRoleDto(Role request);

    List<RoleModal> toRoleDto(List<Role> list);

    @Mapping(target = "auditSection", ignore = true)
    Role toRoleEntity(RoleModal request);

    @Mapping(target = "auditSection", ignore = true)
    Role updateRoleEntity(RoleModal request, @MappingTarget Role Role);

    @Mapping(target = "password", ignore = true)
    UserModal toUserDto(User request);

    List<UserModal> toUserDto(List<User> list);

    @Mapping(target = "auditSection", ignore = true)
    User toUserEntity(UserModal request);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "auditSection", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "lastSuccessfulLoginAt", ignore = true)
    User updateUserEntity(UserModal request, @MappingTarget User User);

}
