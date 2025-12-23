package com.vivek.vsketch.controller;

import com.vivek.vsketch.model.Order;
import com.vivek.vsketch.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private OrderRepository orderRepository;

    // HOME PAGE (shows completed sketches section)
    @GetMapping("/")
    public String home(Model model) {

        List<Order> completedOrders =
                orderRepository
                        .findByStatusAndCompletedImageUrlIsNotNullAndReviewIsNotNull("PAID");

        model.addAttribute("completedOrders", completedOrders);
        return "index";
    }

    // ⭐ REVIEWS PAGE (navbar -> Reviews ⭐)
    @GetMapping("/reviews")
    public String reviewsPage(Model model) {

        List<Order> completedOrders =
                orderRepository
                        .findByStatusAndCompletedImageUrlIsNotNullAndReviewIsNotNull("PAID");

        model.addAttribute("completedOrders", completedOrders);
        return "reviews";
    }

    // SUCCESS PAGE (after order)
    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
