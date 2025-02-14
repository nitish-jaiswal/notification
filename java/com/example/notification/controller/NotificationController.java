package com.example.notification.controller;
import com.example.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "Endpoints for sending various types of notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/test-env")
    public String testEnv() {
        String username = System.getenv("MAIL_USERNAME");
        String password = System.getenv("MAIL_PASSWORD");
        return "Username exists: " + (username != null) + ", Password exists: " + (password != null);
    }
    @GetMapping("/test")
    public String test() {
        return "Application is running!";
    }
    @Operation(
            summary = "Send ticket purchase confirmation",
            description = "Sends an email notification for successful ticket purchase"
    )
    @ApiResponse(responseCode = "200", description = "Notification sent successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/ticket-purchase")
    public void notifyTicketPurchase(
            @Parameter(description = "Recipient's email address")
            @RequestParam String email,
            @Parameter(description = "Details of the purchased ticket")
            @RequestParam String ticketDetails) {
        notificationService.notifyTicketPurchaseSuccess(email, ticketDetails);
    }

    @Operation(
            summary = "Send penalty notification",
            description = "Sends an email notification for penalty charges"
    )
    @ApiResponse(responseCode = "200", description = "Notification sent successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/penalty")
    public void notifyPenalty(
            @Parameter(description = "Recipient's email address")
            @RequestParam String email,
            @Parameter(description = "Penalty amount")
            @RequestParam double amount) {
        notificationService.notifyPenaltyCharged(email, amount);
    }

    @Operation(
            summary = "Send SOS alert",
            description = "Sends an emergency SOS alert to station manager"
    )
    @ApiResponse(responseCode = "200", description = "Alert sent successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/sos")
    public void notifySOS(
            @Parameter(description = "Station manager's email address")
            @RequestParam String managerEmail,
            @Parameter(description = "Name of the station")
            @RequestParam String stationName,
            @Parameter(description = "Details of the user who triggered SOS")
            @RequestParam String userDetails) {
        notificationService.notifySOS(managerEmail, stationName, userDetails);
    }
}