package com.shopspiker.auth.service;

import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.modal.ClientModal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

public interface ClientService {
	
	public ClientModal getById(String id);

    public Page<ClientModal> get(Specification<Client> spec, Integer pageNo, Integer pageSize, String sortBy,
                                 String sortDirection);

    public ClientModal create(ClientModal request);

    public ClientModal update(String id, ClientModal request);

    public void deleteById(String id);



    public Client findById(String id);

    public Client findByIdAndActive(String id);

    public boolean checkAuthorizedGrantType(String grantType);

    public boolean checkAuthority(String authority);

    public Client getClient();

    public Client findByIdAndSecret(String clientId, String clientSecret);

}
