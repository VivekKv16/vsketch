package com.vivek.vsketch.controller;

import com.vivek.vsketch.model.Order;
import com.vivek.vsketch.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    @Autowired
    private OrderRepository orderRepository;

    // ================= SHOW REVIEW PAGE =================
    @GetMapping("/review/{id}")
    public String reviewPage(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow();
        model.addAttribute("order", order);

        // 🔥 MUST match file name: reviews.html
        return "reviews";
    }

    // ================= SUBMIT REVIEW =================
    @PostMapping("/review/{id}")
    public String submitReview(
            @PathVariable Long id,
            @RequestParam String review,
            @RequestParam Integer rating
    ) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setReview(review);
        order.setRating(rating);
        orderRepository.save(order);

        // ✅ Redirect so latest reviews appear immediately
        return "redirect:/reviews";
        // OR 👉 return "redirect:/"; if you want home page
    }
}
