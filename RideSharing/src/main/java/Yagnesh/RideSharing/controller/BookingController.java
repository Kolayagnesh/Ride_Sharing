package Yagnesh.RideSharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import Yagnesh.RideSharing.dto.BookingRequest;
import Yagnesh.RideSharing.model.Booking;
import Yagnesh.RideSharing.model.Ride;
import Yagnesh.RideSharing.model.User;
import Yagnesh.RideSharing.repository.BookingRepository;
import Yagnesh.RideSharing.repository.RideRepository;
import Yagnesh.RideSharing.repository.UserRepository;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    @PostMapping
    public Booking bookRide(@RequestBody BookingRequest request) {

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        String email = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findByEmail(email).get();

        if (ride.getAvailableSeats() < request.getSeatsBooked()) {
            throw new RuntimeException("Not enough seats available");
        }

        ride.setAvailableSeats(ride.getAvailableSeats() - request.getSeatsBooked());
        rideRepository.save(ride);
        Booking booking = new Booking();
        booking.setRide(ride);
        booking.setUser(user);
        booking.setSeatsBooked(request.getSeatsBooked());
        double distance = 100;

        double total = distance * ride.getPricePerKm() * request.getSeatsBooked();

        booking.setTotalAmount(total);
        return bookingRepository.save(booking);
    }
}