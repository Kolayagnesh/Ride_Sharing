package Yagnesh.RideSharing.model;

import Yagnesh.RideSharing.model.Booking;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Booking booking;

    private double amount;

    private String status; // SUCCESS / FAILED

    private String paymentMethod; // UPI / CARD

    private String transactionId;
}