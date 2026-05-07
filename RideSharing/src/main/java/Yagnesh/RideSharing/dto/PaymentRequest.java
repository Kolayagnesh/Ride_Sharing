package Yagnesh.RideSharing.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long bookingId;
    private String paymentMethod;
}