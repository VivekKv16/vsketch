package com.vivek.vsketch.controller;

import org.springframework.beans.factory.annotation.Value;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vivek.vsketch.model.Order;
import com.vivek.vsketch.repository.OrderRepository;
import com.vivek.vsketch.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private EmailService emailService;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;


    // ================= LOGIN =================

    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session
    ) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            session.setAttribute("admin", true);
            return "redirect:/admin/orders";
        }

        return "redirect:/admin/login?error";
    }

    // ================= VIEW ORDERS =================

    @GetMapping("/orders")
    public String viewOrders(HttpSession session, Model model) {

        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    // ================= MARK PAID (✅ ADDED HERE) =================

    @PostMapping("/mark-paid/{id}")
    public String markPaid(
            @PathVariable Long id,
            HttpSession session
    ) {

        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("PAID");
        orderRepository.save(order);

        return "redirect:/admin/orders";
    }

    // ================= UPLOAD COMPLETED SKETCH =================

    @PostMapping("/upload-completed/{id}")
    public String uploadCompletedSketch(
            @PathVariable Long id,
            @RequestParam("completedPhoto") MultipartFile completedPhoto,
            HttpSession session
    ) throws Exception {

        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        Order order = orderRepository.findById(id).orElseThrow();

        // Upload to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(
                completedPhoto.getBytes(),
                ObjectUtils.asMap("folder", "vsketch/completed")
        );

        String completedImageUrl = uploadResult.get("secure_url").toString();

        // Save to DB
        order.setCompletedImageUrl(completedImageUrl);
        order.setStatus("PAID");
        orderRepository.save(order);

        // 📧 SEND COMPLETION EMAIL TO CUSTOMER
        emailService.sendCompletedOrderMail(
                order.getEmail(),
                order.getName(),
                completedImageUrl,
                order.getId()
        );

        return "redirect:/admin/orders";
    }

    // ================= LOGOUT =================

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
