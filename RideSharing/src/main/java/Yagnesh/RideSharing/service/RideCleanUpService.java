package Yagnesh.RideSharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import Yagnesh.RideSharing.repository.RideRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideCleanUpService {

    private final RideRepository rideRepository;

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void deleteExpiredRides() {

        rideRepository.deleteAll(
                rideRepository.findAll().stream()
                        .filter(ride -> ride.getRideDateTime().isBefore(LocalDateTime.now()))
                        .toList()
        );

        System.out.println("Expired rides cleaned");
    }
}