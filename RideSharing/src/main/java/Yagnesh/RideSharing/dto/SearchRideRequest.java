package Yagnesh.RideSharing.dto;

import lombok.Data;

@Data
public class SearchRideRequest {
    private String source;
    private String destination;
}