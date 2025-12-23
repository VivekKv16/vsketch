package com.vivek.vsketch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "gallery_images")
public class GalleryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    public GalleryImage() {}

    public GalleryImage(String imageName) {
        this.imageName = imageName;
    }

    public Long getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }
}
