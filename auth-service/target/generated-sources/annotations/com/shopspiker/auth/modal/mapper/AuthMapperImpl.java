package com.shopspiker.auth.modal.mapper;

import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.jpa.entity.Permission;
import com.shopspiker.auth.jpa.entity.Role;
import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.modal.ClientModal;
import com.shopspiker.auth.modal.PermissionModal;
import com.shopspiker.auth.modal.RoleModal;
import com.shopspiker.auth.modal.UserModal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-22T19:36:28+0400",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AuthMapperImpl implements AuthMapper {

    @Override
    public ClientModal toClientDto(Client request) {
        if ( request == null ) {
            return null;
        }

        ClientModal.ClientModalBuilder clientModal = ClientModal.builder();

        clientModal.id( request.getId() );
        clientModal.secret( request.getSecret() );
        List<AuthorizedGrantTypeEnum> list = request.getAuthorizedGrantTypes();
        if ( list != null ) {
            clientModal.authorizedGrantTypes( new ArrayList<AuthorizedGrantTypeEnum>( list ) );
        }
        List<AuthoritiesEnum> list1 = request.getAuthorities();
        if ( list1 != null ) {
            clientModal.authorities( new ArrayList<AuthoritiesEnum>( list1 ) );
        }
        clientModal.accessTokenValiditySeconds( request.getAccessTokenValiditySeconds() );
        clientModal.refreshTokenValiditySeconds( request.getRefreshTokenValiditySeconds() );
        clientModal.active( request.isActive() );
        clientModal.auditSection( request.getAuditSection() );

        return clientModal.build();
    }

    @Override
    public List<ClientModal> toClientDto(List<Client> list) {
        if ( list == null ) {
            return null;
        }

        List<ClientModal> list1 = new ArrayList<ClientModal>( list.size() );
        for ( Client client : list ) {
            list1.add( toClientDto( client ) );
        }

        return list1;
    }

    @Override
    public Client toClientEntity(ClientModal request) {
        if ( request == null ) {
            return null;
        }

        Client client = new Client();

        client.setId( request.getId() );
        client.setSecret( request.getSecret() );
        List<AuthorizedGrantTypeEnum> list = request.getAuthorizedGrantTypes();
        if ( list != null ) {
            client.setAuthorizedGrantTypes( new ArrayList<AuthorizedGrantTypeEnum>( list ) );
        }
        List<AuthoritiesEnum> list1 = request.getAuthorities();
        if ( list1 != null ) {
            client.setAuthorities( new ArrayList<AuthoritiesEnum>( list1 ) );
        }
        client.setAccessTokenValiditySeconds( request.getAccessTokenValiditySeconds() );
        client.setRefreshTokenValiditySeconds( request.getRefreshTokenValiditySeconds() );
        client.setActive( request.isActive() );

        return client;
    }

    @Override
    public Client updateClientEntity(ClientModal request, Client client) {
        if ( request == null ) {
            return client;
        }

        client.setId( request.getId() );
        client.setSecret( request.getSecret() );
        if ( client.getAuthorizedGrantTypes() != null ) {
            List<AuthorizedGrantTypeEnum> list = request.getAuthorizedGrantTypes();
            if ( list != null ) {
                client.getAuthorizedGrantTypes().clear();
                client.getAuthorizedGrantTypes().addAll( list );
            }
            else {
                client.setAuthorizedGrantTypes( null );
            }
        }
        else {
            List<AuthorizedGrantTypeEnum> list = request.getAuthorizedGrantTypes();
            if ( list != null ) {
                client.setAuthorizedGrantTypes( new ArrayList<AuthorizedGrantTypeEnum>( list ) );
            }
        }
        if ( client.getAuthorities() != null ) {
            List<AuthoritiesEnum> list1 = request.getAuthorities();
            if ( list1 != null ) {
                client.getAuthorities().clear();
                client.getAuthorities().addAll( list1 );
            }
            else {
                client.setAuthorities( null );
            }
        }
        else {
            List<AuthoritiesEnum> list1 = request.getAuthorities();
            if ( list1 != null ) {
                client.setAuthorities( new ArrayList<AuthoritiesEnum>( list1 ) );
            }
        }
        client.setAccessTokenValiditySeconds( request.getAccessTokenValiditySeconds() );
        client.setRefreshTokenValiditySeconds( request.getRefreshTokenValiditySeconds() );
        client.setActive( request.isActive() );

        return client;
    }

    @Override
    public PermissionModal toPermissionDto(Permission request) {
        if ( request == null ) {
            return null;
        }

        PermissionModal.PermissionModalBuilder permissionModal = PermissionModal.builder();

        permissionModal.id( request.getId() );
        permissionModal.name( request.getName() );
        permissionModal.group( request.getGroup() );
        permissionModal.active( request.isActive() );
        permissionModal.auditSection( request.getAuditSection() );

        return permissionModal.build();
    }

    @Override
    public List<PermissionModal> toPermissionDto(List<Permission> list) {
        if ( list == null ) {
            return null;
        }

        List<PermissionModal> list1 = new ArrayList<PermissionModal>( list.size() );
        for ( Permission permission : list ) {
            list1.add( toPermissionDto( permission ) );
        }

        return list1;
    }

    @Override
    public Permission toPermissionEntity(PermissionModal request) {
        if ( request == null ) {
            return null;
        }

        Permission permission = new Permission();

        permission.setId( request.getId() );
        permission.setName( request.getName() );
        permission.setGroup( request.getGroup() );
        permission.setActive( request.isActive() );

        return permission;
    }

    @Override
    public Permission updatePermissionEntity(PermissionModal request, Permission permission) {
        if ( request == null ) {
            return permission;
        }

        permission.setId( request.getId() );
        permission.setName( request.getName() );
        permission.setGroup( request.getGroup() );
        permission.setActive( request.isActive() );

        return permission;
    }

    @Override
    public RoleModal toRoleDto(Role request) {
        if ( request == null ) {
            return null;
        }

        RoleModal.RoleModalBuilder roleModal = RoleModal.builder();

        roleModal.id( request.getId() );
        roleModal.name( request.getName() );
        roleModal.active( request.isActive() );
        roleModal.permissions( toPermissionDto( request.getPermissions() ) );
        roleModal.auditSection( request.getAuditSection() );

        return roleModal.build();
    }

    @Override
    public List<RoleModal> toRoleDto(List<Role> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleModal> list1 = new ArrayList<RoleModal>( list.size() );
        for ( Role role : list ) {
            list1.add( toRoleDto( role ) );
        }

        return list1;
    }

    @Override
    public Role toRoleEntity(RoleModal request) {
        if ( request == null ) {
            return null;
        }

        Role role = new Role();

        role.setId( request.getId() );
        role.setName( request.getName() );
        role.setActive( request.isActive() );
        role.setPermissions( permissionModalListToPermissionList( request.getPermissions() ) );

        return role;
    }

    @Override
    public Role updateRoleEntity(RoleModal request, Role Role) {
        if ( request == null ) {
            return Role;
        }

        Role.setId( request.getId() );
        Role.setName( request.getName() );
        Role.setActive( request.isActive() );
        if ( Role.getPermissions() != null ) {
            List<Permission> list = permissionModalListToPermissionList( request.getPermissions() );
            if ( list != null ) {
                Role.getPermissions().clear();
                Role.getPermissions().addAll( list );
            }
            else {
                Role.setPermissions( null );
            }
        }
        else {
            List<Permission> list = permissionModalListToPermissionList( request.getPermissions() );
            if ( list != null ) {
                Role.setPermissions( list );
            }
        }

        return Role;
    }

    @Override
    public UserModal toUserDto(User request) {
        if ( request == null ) {
            return null;
        }

        UserModal.UserModalBuilder userModal = UserModal.builder();

        userModal.id( request.getId() );
        userModal.firstName( request.getFirstName() );
        userModal.lastName( request.getLastName() );
        userModal.email( request.getEmail() );
        userModal.emailVerified( request.isEmailVerified() );
        userModal.phone( request.getPhone() );
        userModal.phoneVerified( request.isPhoneVerified() );
        userModal.active( request.isActive() );
        userModal.lastSuccessfulLoginAt( request.getLastSuccessfulLoginAt() );
        userModal.roles( toRoleDto( request.getRoles() ) );
        userModal.auditSection( request.getAuditSection() );

        return userModal.build();
    }

    @Override
    public List<UserModal> toUserDto(List<User> list) {
        if ( list == null ) {
            return null;
        }

        List<UserModal> list1 = new ArrayList<UserModal>( list.size() );
        for ( User user : list ) {
            list1.add( toUserDto( user ) );
        }

        return list1;
    }

    @Override
    public User toUserEntity(UserModal request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setId( request.getId() );
        user.setEmail( request.getEmail() );
        user.setEmailVerified( request.isEmailVerified() );
        user.setPassword( request.getPassword() );
        user.setFirstName( request.getFirstName() );
        user.setLastName( request.getLastName() );
        user.setPhone( request.getPhone() );
        user.setPhoneVerified( request.isPhoneVerified() );
        user.setActive( request.isActive() );
        user.setLastSuccessfulLoginAt( request.getLastSuccessfulLoginAt() );
        user.setRoles( roleModalListToRoleList( request.getRoles() ) );

        return user;
    }

    @Override
    public User updateUserEntity(UserModal request, User User) {
        if ( request == null ) {
            return User;
        }

        User.setId( request.getId() );
        User.setEmail( request.getEmail() );
        User.setFirstName( request.getFirstName() );
        User.setLastName( request.getLastName() );
        User.setPhone( request.getPhone() );
        if ( User.getRoles() != null ) {
            List<Role> list = roleModalListToRoleList( request.getRoles() );
            if ( list != null ) {
                User.getRoles().clear();
                User.getRoles().addAll( list );
            }
            else {
                User.setRoles( null );
            }
        }
        else {
            List<Role> list = roleModalListToRoleList( request.getRoles() );
            if ( list != null ) {
                User.setRoles( list );
            }
        }

        return User;
    }

    protected List<Permission> permissionModalListToPermissionList(List<PermissionModal> list) {
        if ( list == null ) {
            return null;
        }

        List<Permission> list1 = new ArrayList<Permission>( list.size() );
        for ( PermissionModal permissionModal : list ) {
            list1.add( toPermissionEntity( permissionModal ) );
        }

        return list1;
    }

    protected List<Role> roleModalListToRoleList(List<RoleModal> list) {
        if ( list == null ) {
            return null;
        }

        List<Role> list1 = new ArrayList<Role>( list.size() );
        for ( RoleModal roleModal : list ) {
            list1.add( toRoleEntity( roleModal ) );
        }

        return list1;
    }
}
