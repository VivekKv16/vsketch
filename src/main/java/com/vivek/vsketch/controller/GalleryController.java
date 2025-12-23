package com.vivek.vsketch.controller;

import com.vivek.vsketch.repository.GalleryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GalleryController {

    private final GalleryRepository galleryRepository;

    public GalleryController(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        model.addAttribute("images", galleryRepository.findAll());
        return "gallery";
    }
}
