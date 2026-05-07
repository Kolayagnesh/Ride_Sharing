package Yagnesh.RideSharing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String destination;

    private LocalDateTime rideDateTime;

    private int availableSeats;
    private double pricePerKm;
    @ManyToOne
    private User driver;
}