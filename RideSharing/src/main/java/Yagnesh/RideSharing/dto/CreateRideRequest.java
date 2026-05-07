package Yagnesh.RideSharing.dto;

import lombok.Data;

@Data
public class CreateRideRequest {
    private String source;
    private String destination;
    private String rideDateTime;
    private int availableSeats;

    private double pricePerKm;
}