package com.interview.client.Service;

import com.interview.client.Dto.Client;
import com.interview.client.Dto.VerificationRequest;
import com.interview.client.Dto.VerificationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class VerificationClientService {

    private final RestClient restClient;

    public VerificationClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    public VerificationResponse verifyClient(Client client) {

        VerificationRequest request = new VerificationRequest();
        request.setIcNumber(client.getIcNumber());

        log.info("Calling Verification API for IC={}", client.getIcNumber());

        VerificationResponse response = restClient.post()
                        .uri("http://localhost:8080/mock/verify")
                        .body(request)
                        .retrieve()
                        .body(VerificationResponse.class);

        log.info("Verification Response={}", response);

        return response;
    }
}
