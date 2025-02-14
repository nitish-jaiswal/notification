package com.example.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;

    public void notifyTicketPurchaseSuccess(String email, String ticketDetails) {
        String subject = "Ticket Purchase Confirmation";
        String body = String.format("Thank you for your purchase! Here are your ticket details:\n%s", ticketDetails);
        emailService.sendEmail(email, subject, body);
    }

    public void notifyPenaltyCharged(String email, double amount) {
        String subject = "Penalty Notification";
        String body = String.format("A penalty of $%.2f has been charged to your account.", amount);
        emailService.sendEmail(email, subject, body);
    }

    public void notifySOS(String managerEmail, String stationName, String userDetails) {
        String subject = "SOS Alert - Immediate Action Required";
        String body = String.format("SOS alert from station: %s\nUser Details: %s", stationName, userDetails);
        emailService.sendEmail(managerEmail, subject, body);
    }
}