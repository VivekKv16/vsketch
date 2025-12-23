package com.vivek.vsketch.repository;

import com.vivek.vsketch.model.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<GalleryImage, Long> {
}
