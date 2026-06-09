package com.interview.client.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interview.client.Dto.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}