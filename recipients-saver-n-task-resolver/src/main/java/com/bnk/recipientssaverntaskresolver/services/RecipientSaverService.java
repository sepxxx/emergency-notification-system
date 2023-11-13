package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.repositories.RecipientListNameRepository;
import com.bnk.recipientssaverntaskresolver.repositories.RecipientRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipientSaverService {
    RecipientRepository recipientRepository;
    RecipientListNameRepository recipientListNameRepository;

    @Transactional
    public void saveRecipients(List<Recipient> recipientList, String recipientsListName) {
        List<Recipient> recipientListWithIds = recipientRepository.saveAll(recipientList);

        recipientListNameRepository.findAllByName(recipientsListName).appendRecipientList(
                recipientListWithIds
        );


    }
}
