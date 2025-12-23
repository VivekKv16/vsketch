package com.vivek.vsketch.repository;

import com.vivek.vsketch.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatusAndCompletedImageUrlIsNotNullAndReviewIsNotNull(
            String status
    );
}
