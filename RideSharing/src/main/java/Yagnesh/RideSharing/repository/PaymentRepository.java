package Yagnesh.RideSharing.repository;

import Yagnesh.RideSharing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
