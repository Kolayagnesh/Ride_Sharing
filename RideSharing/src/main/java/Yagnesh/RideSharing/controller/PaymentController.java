package Yagnesh.RideSharing.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import Yagnesh.RideSharing.model.Booking;
import Yagnesh.RideSharing.repository.BookingRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final BookingRepository bookingRepository;
    private final RazorpayClient razorpayClient; // ✅ FIX 1

    // 🔥 CREATE ORDER
    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestParam Long bookingId) throws Exception {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        JSONObject options = new JSONObject();
        options.put("amount", (int)(booking.getTotalAmount() * 100)); // paisa
        options.put("currency", "INR");
        options.put("receipt", "order_" + bookingId);

        Order order = razorpayClient.orders.create(options);

        Map<String, Object> response = new HashMap<>();
        response.put("id", order.get("id"));
        response.put("amount", order.get("amount"));

        return response;
    }

    // 🔥 VERIFY PAYMENT
    @PostMapping("/verify")
    public String verifyPayment(@RequestBody Map<String, String> data) throws Exception {

        String paymentId = data.get("razorpay_payment_id");
        String orderId = data.get("razorpay_order_id");
        String signature = data.get("razorpay_signature");
        Long bookingId = Long.parseLong(data.get("bookingId"));

        // 🔐 Create expected signature
        String payload = orderId + "|" + paymentId;

        String generatedSignature = hmacSHA256(payload, "YOUR_SECRET");

        if (!generatedSignature.equals(signature)) {
            throw new RuntimeException("Invalid payment!");
        }

        // ✅ Payment verified → update booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus("PAID");
        bookingRepository.save(booking);

        return "Payment Verified Successfully";
    }
    private String hmacSHA256(String data, String secret) throws Exception {

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(secretKey);

        byte[] hash = mac.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(hash);
    }
}