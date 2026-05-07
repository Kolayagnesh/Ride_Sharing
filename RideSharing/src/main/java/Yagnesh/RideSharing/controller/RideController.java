package Yagnesh.RideSharing.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import Yagnesh.RideSharing.dto.CreateRideRequest;
import Yagnesh.RideSharing.model.Ride;
import Yagnesh.RideSharing.model.User;
import Yagnesh.RideSharing.repository.RideRepository;
import Yagnesh.RideSharing.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    @PostMapping
    public Ride createRide(@RequestBody CreateRideRequest request) {
        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User driver = userRepository.findByEmail(email).orElseThrow();

        Ride ride = new Ride();
        double distance = 100; // dummy (later Google Maps)
        ride.setSource(request.getSource());
        ride.setDestination(request.getDestination());
        ride.setRideDateTime(LocalDateTime.parse(request.getRideDateTime()));
        ride.setAvailableSeats(request.getAvailableSeats());
        ride.setDriver(driver);
        ride.setPricePerKm(request.getPricePerKm());
        return rideRepository.save(ride);
    }
    @GetMapping
    public List<Ride> searchRides(
            @RequestParam String source,
            @RequestParam String destination) {
        return rideRepository.findBySourceAndDestination(source, destination);
    }
}