package com.interview.client.Controller;

import com.interview.client.Dto.PaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.interview.client.Dto.ResponseStructure;
import com.interview.client.Dto.Client;
import com.interview.client.Service.ClientService;

@RestController
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	@PostMapping("/client")
	public ResponseStructure<Client> insertClient(@RequestBody Client client){
		return clientService.insertClient(client);
	}
	
	@GetMapping("/client/{id}")
	public ResponseStructure<Client> getClientById(@PathVariable int id) {
		return clientService.getClientById(id);
	}

	@GetMapping("/client")
	public ResponseStructure<PaginationResponse<Client>> getAllClient(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String direction) {

		return clientService.getAllClient(
				page,
				size,
				sortBy,
				direction);
	}

	
	@PutMapping("/client/{id}")
	public ResponseStructure<Client> updateClient(@RequestBody Client client, @PathVariable Integer id) {
		return clientService.updateClient(client, id);
	}
	
	@DeleteMapping("/client/{id}")
	public ResponseStructure<String> deleteClient(@PathVariable int id) {
		return clientService.deleteClient(id);
	}

	@GetMapping("/ping")
	public String ping() {
		return "Ping successfully !!";
	}
}
