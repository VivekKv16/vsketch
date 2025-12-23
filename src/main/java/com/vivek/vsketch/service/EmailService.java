package com.vivek.vsketch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendOrderMail(
            String to,
            String name,
            String email,
            String phone,
            String type,
            double amount,
            String imageName
    ) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("📸 New Sketch Order Received - VSketch");
            message.setText(
                    "New Order Received\n\n" +
                            "Name: " + name + "\n" +
                            "Email: " + email + "\n" +
                            "Phone: " + phone + "\n" +
                            "Sketch Type: " + type + "\n" +
                            "Amount: ₹" + amount + "\n" +
                            "Image: " + imageName + "\n" +
                            "Status: PENDING"
            );

            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("Order email skipped (SMTP blocked on Render)");
        }
    }

    // ✅ FIXED
    @Async
    public void sendCompletedOrderMail(
            String toEmail,
            String customerName,
            String completedImageUrl,
            Long orderId
    ) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("🎉 Your Sketch is Ready | VSketch");

            message.setText(
                    "Hi " + customerName + ",\n\n" +
                            "Great news! Your sketch is completed 🎨✨\n\n" +
                            "View your completed sketch here:\n" +
                            completedImageUrl + "\n\n" +
                            "Please share your feedback:\n" +
                            "https://vsketch.onrender.com/review/" + orderId + "\n\n" +
                            "Thank you for choosing VSketch ❤️"
            );

            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("Completion email skipped (SMTP blocked on Render)");
        }
    }
}
