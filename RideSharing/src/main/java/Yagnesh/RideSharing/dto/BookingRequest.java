package Yagnesh.RideSharing.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private Long rideId;
    private int seatsBooked;
}