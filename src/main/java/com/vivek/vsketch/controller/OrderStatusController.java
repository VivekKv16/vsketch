package com.vivek.vsketch.controller;

import com.vivek.vsketch.model.Order;
import com.vivek.vsketch.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderStatusController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order-status/{id}")
    public String orderStatus(@PathVariable Long id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow();
        model.addAttribute("order", order);
        return "order-status";
    }
}
