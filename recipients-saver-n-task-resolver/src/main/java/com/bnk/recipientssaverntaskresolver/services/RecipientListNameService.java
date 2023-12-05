package com.bnk.recipientssaverntaskresolver.services;


import com.bnk.miscellaneous.entities.RecipientList;
import com.bnk.miscellaneous.entities.User;
import com.bnk.recipientssaverntaskresolver.exceptions.NotFoundException;
import com.bnk.recipientssaverntaskresolver.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecipientListNameService {
    UserRepository userRepository;

    public List<String> getAllListNamesUserHave(String currentUsername) {
        log.info(" getAllListNamesUserHave currentUsername: {} ", currentUsername);
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return user.getRecipientLists().stream().map(RecipientList::getName).collect(Collectors.toList());
    }
}
