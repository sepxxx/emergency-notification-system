package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.dtos.responses.UserDto;
import com.bnk.recipientssaverntaskresolver.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    public Set<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(u->new UserDto(u.getId(), u.getUsername(), u.getRoles(), u.getImageData()))
                .collect(Collectors.toSet());
    }
}
