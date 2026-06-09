package com.interview.client.Dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.interview.client.Dto.Client;
import com.interview.client.Repository.ClientRepository;

@Repository
public class ClientDao {
	
	@Autowired
	ClientRepository clientRepository;
	
	public Client insertClient(Client client) {
		return clientRepository.save(client);
	}
	
	public Client getClientById(int id) {
		Optional<Client> optional = clientRepository.findById(id);
		if(optional.isEmpty()) {
			return null;
		} else return optional.get();
	}

	public Page<Client> getAllClient(Pageable pageable) {

		return clientRepository.findAll(pageable);
	}
	
	public Client updateClient(Client client, int id) {
		Client existingClient = getClientById(id);
		if(existingClient != null) {
			existingClient.setFirstName(client.getFirstName());
			existingClient.setLastName(client.getLastName());
			existingClient.setIcNumber(client.getIcNumber());
			return clientRepository.save(existingClient);
		} else return null;
	}
	
	public Boolean deleteClient(int id) {
		Optional<Client> optional = clientRepository.findById(id);
		if(optional.isPresent()) {
			clientRepository.delete(optional.get());
			return true;
		} else return false;
	}

}