package com.bnk.recipientssaverntaskresolver.controllers;

// ImageController.java
import com.bnk.recipientssaverntaskresolver.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/user/{userId}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long userId) {
        byte[] imageData = imageService.getUserImage(userId);
        return ResponseEntity.ok().body(imageData);
    }

    @PostMapping("/user/{userId}/upload-image")
    public ResponseEntity<?> handleImageUpload(@PathVariable Long userId, @RequestParam("image") MultipartFile image) {
        imageService.uploadImage(userId, image);
        return ResponseEntity.ok().build();
    }
}

