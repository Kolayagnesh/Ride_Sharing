package Yagnesh.RideSharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Yagnesh.RideSharing.model.Ride;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findBySourceAndDestination(String source, String destination);
}