package Yagnesh.RideSharing.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Ride ride;
    private double totalAmount;
    private int seatsBooked;
    private String status;
}