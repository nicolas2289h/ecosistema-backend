package com.semillero.ecosistemas.controller;
import com.semillero.ecosistemas.service.CloudinaryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/test")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return cloudinaryService.uploadImage(file);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error uploading image";
        }
    }
}
