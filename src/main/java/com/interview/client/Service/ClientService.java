package com.interview.client.Service;

import com.interview.client.Dto.VerificationResponse;
import com.interview.client.exception.VerificationFailedException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.interview.client.Dao.ClientDao;
import com.interview.client.Dto.Client;
import com.interview.client.Dto.PaginationResponse;
import com.interview.client.Dto.ResponseStructure;

@Service
@Transactional
public class ClientService {
	
	@Autowired
	ClientDao clientDao;

	@Autowired
	private VerificationClientService verificationClientService;

	public ResponseStructure<Client> insertClient(Client client){
		ResponseStructure<Client> responseStructure = new ResponseStructure<Client>();


		// Call 3rd party API (use mock API here) to perform verification prior insert client data to DB
		VerificationResponse verificationResponse = verificationClientService.verifyClient(client);

		if (verificationResponse == null) {
			throw new RuntimeException(
					"Verification service unavailable");
		}

		if (!"PASS".equalsIgnoreCase(verificationResponse.getStatus())) {
//			throw new RuntimeException("Client verification failed. Risk Level: " + verificationResponse.getRiskLevel());
			throw new VerificationFailedException(verificationResponse.getMessage());
		}

		// TODO: set time out
		// TODO: Implement retry mechanism


		// insert client data
		Client client1 = clientDao.insertClient(client);
		if(client1 != null) {
			responseStructure.setData(client1);
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Client inserted successfully");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseStructure.setMessage("Client has failed to save");
		}

		return responseStructure;
	}

	public ResponseStructure<Client> getClientById(Integer id){
		ResponseStructure<Client> responseStructure = new ResponseStructure<Client>();
		Client client = clientDao.getClientById(id);
		if(client != null) {
			responseStructure.setData(client);
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Client retrieved successfully");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("Client not found");
		}
		return responseStructure;
	}

	public ResponseStructure<PaginationResponse<Client>> getAllClient(int page, int size, String sortBy, String direction) {

		Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Client> clientPage = clientDao.getAllClient(pageable);

		PaginationResponse<Client> paginationResponse = new PaginationResponse<>();
		paginationResponse.setRecords(clientPage.getContent());
		paginationResponse.setPageNumber(clientPage.getNumber());
		paginationResponse.setPageSize(clientPage.getSize());
		paginationResponse.setTotalRecords(clientPage.getTotalElements());
		paginationResponse.setTotalPages(clientPage.getTotalPages());
		paginationResponse.setFirst(clientPage.isFirst());
		paginationResponse.setLast(clientPage.isLast());

		ResponseStructure<PaginationResponse<Client>> responseStructure = new ResponseStructure<>();

		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Clients retrieved successfully");
		responseStructure.setData(paginationResponse);

		return responseStructure;
	}

	public ResponseStructure<Client> updateClient(Client client, Integer id){
		ResponseStructure<Client> responseStructure = new ResponseStructure<Client>();
		Client client1 = clientDao.updateClient(client, id);
		if(client1 != null) {
			responseStructure.setData(client1);
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Client updated successfully");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("Client not found for update");
		}
		return responseStructure;
	}

	public ResponseStructure<String> deleteClient(Integer id){
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		boolean isTrue = clientDao.deleteClient(id);
		if(isTrue) {
			responseStructure.setData("Client deleted");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Client deleted successfully");
		} else {
			responseStructure.setData(null);
			responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("Client not found for deletion");
		}
		return responseStructure;
	}


}
