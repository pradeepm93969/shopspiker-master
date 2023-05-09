package com.shopspiker.auth.service.impl;

import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.jpa.repository.ClientRepository;
import com.shopspiker.auth.modal.ClientModal;
import com.shopspiker.auth.modal.mapper.AuthMapper;
import com.shopspiker.auth.service.ClientService;
import com.shopspiker.common.web.exception.CustomApplicationException;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private AuthMapper mapper;
    
    @Override
    @Cacheable(value = "Client", key = "#id")
    public ClientModal getById(String id) {
        return mapper.toClientDto(findById(id));
    }
    
    @Override
    public Page<ClientModal> get(Specification<Client> spec, Integer pageNo, Integer pageSize, String sortBy,
                                 String sortDirection) {
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                Sort.by((StringUtils.isBlank(sortDirection) || sortDirection.equalsIgnoreCase("asc")) ? Direction.ASC
                        : Direction.DESC, sortBy));
        Page<Client> clientPage = repository.findAll(spec, pageable);
        return new PageImpl<>(mapper.toClientDto(clientPage.getContent()), pageable,
                clientPage.getTotalElements());

    }

    @Override
    public ClientModal create(ClientModal request) {
        Client client = mapper.toClientEntity(request);
        try {
            client = repository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "CLIENT_ID_TAKEN", "Client ID is taken");
        }
        return mapper.toClientDto(client);
    }

    @Override
    @CachePut(value = "Client", key = "#id")
    public ClientModal update(String id, ClientModal request) {
        Client client = findById(id);
        mapper.updateClientEntity(request, client);
        try {
            client = repository.save(client);
        } catch (DataIntegrityViolationException e) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "CLIENT_ID_TAKEN", "Client ID is taken");
        }
        return mapper.toClientDto(client);
    }

    @Override
    @CacheEvict(value = "Client", key = "#id")
    public void deleteById(String id) {
        repository.delete(findById(id));
    }
    

    @Override
    public Client findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new CustomApplicationException(HttpStatus.NOT_FOUND, "CLIENT_NOT_FOUND", "Client not found"));
    }

    @Override
    public Client findByIdAndActive(String id) {
        Client client = findById(id);
        if (!client.isActive()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "CLIENT_NOT_ACTIVE", "Client not active");
        }
        return client;
    }

    @Override
    @Cacheable(value = "Client", key = "#id.concat('-').concat(#secret)")
    public Client findByIdAndSecret(String id, String secret) {
        return repository.findByIdAndSecretAndActive(id, secret, true).orElseThrow(
                () -> new CustomApplicationException(HttpStatus.UNAUTHORIZED, "CLIENT_NOT_FOUND", "Client not found"));
    }

    // Other Required Methods
    public Client getClient() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();

            // retrieving credentials the HTTP Authorization Header
            String authorizationCredentials = request.getHeader(HttpHeaders.AUTHORIZATION).substring("Basic".length())
                    .trim();

            // decoding credentials
            String[] decodedCredentials = new String(Base64.getDecoder().decode(authorizationCredentials)).split(":");

            // verifying if the credentials received are valid
            return findByIdAndSecret(decodedCredentials[0], decodedCredentials[1]);

        } catch (Exception e) {
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                    e.getLocalizedMessage());
        }
    }

    public boolean checkAuthorizedGrantType(String grantType) {
        try {
            // Get Client details from Authorization Header
            if (getClient().getAuthorizedGrantTypes().contains(AuthorizedGrantTypeEnum.valueOf(grantType))) {
                return true;
            }
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "UNAUTHORIZED");

        } catch (CustomApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                    e.getLocalizedMessage());
        }
    }

    public boolean checkAuthority(String authority) {
        try {
            // Get Client details from Authorization Header
            if (getClient().getAuthorities().contains(AuthoritiesEnum.valueOf(authority))) {
                return true;
            }
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "UNAUTHORIZED");

        } catch (CustomApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                    e.getLocalizedMessage());
        }
    }

}
