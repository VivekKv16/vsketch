package com.vivek.vsketch.controller;

import com.vivek.vsketch.model.GalleryImage;
import com.vivek.vsketch.repository.GalleryRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin/gallery")
public class GalleryAdminController {

    @Value("${gallery.upload-dir}")
    private String galleryDir;

    private final GalleryRepository galleryRepository;

    public GalleryAdminController(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    // Admin upload page
    @GetMapping
    public String galleryUploadPage(HttpSession session) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }
        return "admin-gallery";
    }

    // Handle upload
    @PostMapping
    public String uploadImage(
            @RequestParam("image") MultipartFile image,
            HttpSession session
    ) throws Exception {

        if (session.getAttribute("admin") == null) {
            return "redirect:/admin/login";
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(galleryDir);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Files.copy(image.getInputStream(), path.resolve(fileName));

        galleryRepository.save(new GalleryImage(fileName));

        return "redirect:/admin/gallery";
    }
}
