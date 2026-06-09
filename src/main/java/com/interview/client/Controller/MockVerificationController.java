package com.interview.client.Controller;

import com.interview.client.Dto.VerificationRequest;
import com.interview.client.Dto.VerificationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
public class MockVerificationController {

    @PostMapping("/verify")
    public VerificationResponse verify(@RequestBody VerificationRequest request) {

        VerificationResponse response = new VerificationResponse();

        // Custom logic - return pass as long ic_number start with 07
        if (request.getIcNumber() != null && request.getIcNumber().startsWith("07")) {
            response.setStatus("PASS");
            response.setScore(750);
            response.setRiskLevel("LOW");
            response.setMessage("Verification Successful");

        } else {

            response.setStatus("FAIL");
            response.setScore(350);
            response.setRiskLevel("HIGH");
            response.setMessage("Verification Failed");
        }

        return response;
    }
}