package com.interview.client.Dto;

import lombok.Data;

@Data
public class VerificationResponse {

    private String status;

    private Integer score;

    private String riskLevel;

    private String message;
}