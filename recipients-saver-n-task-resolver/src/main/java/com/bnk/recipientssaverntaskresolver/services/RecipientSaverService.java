package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.entities.User;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.RecipientList;
import com.bnk.recipientssaverntaskresolver.repositories.RecipientListNameRepository;
import com.bnk.recipientssaverntaskresolver.repositories.RecipientRepository;
import com.bnk.recipientssaverntaskresolver.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipientSaverService {
    RecipientRepository recipientRepository;
    RecipientListNameRepository recipientListNameRepository;
    UserRepository userRepository;

    @Transactional
    public void saveRecipients(List<Recipient> recipientsWithoutIds, String recipientsListName, Long userId) {
        List<Recipient> recipientsWithIds = recipientRepository.saveAll(recipientsWithoutIds);



        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<RecipientList> recipientListOptional = recipientListNameRepository.findByName(recipientsListName);
            RecipientList recipientListWithId;
            if (recipientListOptional.isEmpty())
                recipientListWithId = recipientListNameRepository
                        .save(new RecipientList(recipientsListName, user));
            else
                recipientListWithId = recipientListOptional.get();

            recipientListWithId.appendRecipientList(recipientsWithIds);
        } else {
            throw new IllegalStateException();
        }
    }
}
