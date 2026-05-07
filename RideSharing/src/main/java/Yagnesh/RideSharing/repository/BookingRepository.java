package Yagnesh.RideSharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Yagnesh.RideSharing.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}