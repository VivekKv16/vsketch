package com.vivek.vsketch.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vivek.vsketch.model.Order;
import com.vivek.vsketch.repository.OrderRepository;
import com.vivek.vsketch.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;



    /* =========================
       SHOW ORDER PAGE
    ========================= */
    @GetMapping("/order")
    public String showOrderPage() {
        return "order";
    }

    /* =========================
       HANDLE ORDER SUBMISSION
    ========================= */
    @PostMapping("/order")
    public String submitOrder(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,   // 👈 ADD
            @RequestParam String type,
            @RequestParam MultipartFile photo,
            RedirectAttributes redirectAttributes
    ) throws Exception {


        // 1️⃣ Upload image to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(
                photo.getBytes(),
                ObjectUtils.asMap("folder", "vsketch/orders")
        );

        String imageUrl = uploadResult.get("secure_url").toString();

        // 2️⃣ Save order in DB
        Order order = new Order(
                name,
                email,
                phone,
                type,
                imageUrl,
                500.0,
                "PENDING"
        );

        orderRepository.save(order);

        // 3️⃣ Send email to admin
        try {
            emailService.sendOrderMail(
                    "yourgmail@gmail.com",
                    name,
                    email,
                    phone,
                    type,
                    500.0,
                    imageUrl
            );
        } catch (Exception e) {
            System.out.println("Email failed, but order saved: " + e.getMessage());
        }


        // 4️⃣ Success page data
        redirectAttributes.addFlashAttribute("imageUrl", imageUrl);
        redirectAttributes.addFlashAttribute("amount", 500);
        redirectAttributes.addFlashAttribute("status", "PENDING");

        return "redirect:/success";
    }
}
