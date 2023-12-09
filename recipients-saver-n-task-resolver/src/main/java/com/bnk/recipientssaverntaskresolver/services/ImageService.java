package com.bnk.recipientssaverntaskresolver.services;

// ImageService.java
import com.bnk.miscellaneous.entities.User;
import com.bnk.recipientssaverntaskresolver.exceptions.NotFoundException;
import com.bnk.recipientssaverntaskresolver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private UserRepository userRepository;

    public byte[] getUserImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return user.getImageData();
    }

    public void uploadImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        try {
            user.setImageData(file.getBytes());

            // Логика сохранения изображения в базе данных
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image");
        }
    }
}
