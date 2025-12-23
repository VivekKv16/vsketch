package com.vivek.vsketch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String type;


    @Column(name = "image_url", length = 500)
    private String imageUrl;

    private double amount;

    private String status; // PENDING, PAID

    private LocalDateTime createdAt;

    @Column(length = 15)
    private String phone;


    /* =========================
       COMPLETED SKETCH
    ========================= */
    @Column(length = 500)
    private String completedImageUrl;

    /* =========================
       REVIEW
    ========================= */
    @Column(length = 1000)
    private String review;

    private Integer rating; // 1–5

    /* =========================
       CONSTRUCTORS
    ========================= */
    public Order() {
        this.createdAt = LocalDateTime.now();
    }

    public Order(String name, String email, String phone, String type,
                 String imageUrl, double amount, String status) {

        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }


    /* =========================
       GETTERS & SETTERS
    ========================= */
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCompletedImageUrl() {
        return completedImageUrl;
    }

    public void setCompletedImageUrl(String completedImageUrl) {
        this.completedImageUrl = completedImageUrl;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
