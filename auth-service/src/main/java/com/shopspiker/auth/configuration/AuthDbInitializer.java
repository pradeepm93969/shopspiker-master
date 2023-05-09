package com.shopspiker.auth.configuration;

import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.auth.jpa.constants.PermissionsEnum;
import com.shopspiker.auth.jpa.repository.UserRepository;
import com.shopspiker.auth.modal.ClientModal;
import com.shopspiker.auth.modal.RoleModal;
import com.shopspiker.auth.modal.UserModal;
import com.shopspiker.auth.service.ClientService;
import com.shopspiker.auth.service.RoleService;
import com.shopspiker.auth.service.UserService;
import com.shopspiker.common.jpa.entity.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AuthDbInitializer {

    @Value("${db.init.data:true}")
    private boolean initDefaultData;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        log.info("Inside init() method of AuthDB Initializer");

        if (this.initDefaultData && userRepository.count() == 0) {
            initializeClient();
            initializeRole();
            initializeUser();
        }
    }

    private void initializeClient() {
        List<ClientModal> list = new ArrayList<>();
        list.add(ClientModal.builder()
                        .id("CUSTOMER_WEB_CLIENT")
                        .secret("CUSTOMER_WEB_CLIENT")
                        .active(true)
                        .authorities(new ArrayList<>(Arrays.asList(AuthoritiesEnum.OTP,
                                AuthoritiesEnum.LOGIN, AuthoritiesEnum.REGISTRATION)))
                        .authorizedGrantTypes(new ArrayList<>(Arrays.asList(AuthorizedGrantTypeEnum.PASSWORD,
                                AuthorizedGrantTypeEnum.REFRESH_TOKEN)))
                        .accessTokenValiditySeconds(3600)
                        .refreshTokenValiditySeconds(86400)
                .build());

        list.stream().forEach(e -> clientService.create(e));
    }

    private void initializeRole() {
        List<RoleModal> list = new ArrayList<>();
        list.add(RoleModal.builder()
                .id("ROLE_SUPER_ADMIN").name("Super admin user").active(true)
                .build());
        list.add(RoleModal.builder()
                .id("ROLE_CUSTOMER_SERVICE").name("Customer Service").active(true)
                        .permissions(Arrays.asList(PermissionsEnum.MANAGE_CUSTOMER,
                                PermissionsEnum.READ_CUSTOMER))
                .build());
        list.add(RoleModal.builder()
                .id("ROLE_B2C_CUSTOMER").name("B2C customer").active(true)
                .build());

        list.stream().forEach(e -> roleService.create(e));
    }
    private void initializeUser() {
        List<UserModal> list = new ArrayList<>();
        list.add(UserModal.builder()
                        .email("admin@shopspiker.com")
                        .password("shopspiker@123")
                        .phone(Phone.builder().countryCode("971").phoneNumber("543305555").build())
                        .firstName("admin")
                        .lastName("admin")
                        .active(true)
                        .roles(Arrays.asList(RoleModal.builder()
                                .id("ROLE_SUPER_ADMIN")
                                .build()))
                .build());
        list.add(UserModal.builder()
                .email("user@shopspiker.com")
                .password("shopspiker@123")
                .phone(Phone.builder().countryCode("971").phoneNumber("543306666").build())
                .firstName("admin")
                .lastName("admin")
                .active(true)
                .roles(Arrays.asList(RoleModal.builder()
                        .id("ROLE_B2C_CUSTOMER")
                        .build()))
                .build());

        list.stream().forEach(e -> userService.create(e));
    }

}
