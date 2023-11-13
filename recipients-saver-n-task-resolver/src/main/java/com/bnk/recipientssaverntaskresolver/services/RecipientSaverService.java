package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.repositories.RecipientRepository;
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

//    @Transactional(propagation = Propagation.NEVER)
    public void saveRecipients(List<Recipient> recipientList) {
        recipientRepository.saveAll(recipientList);
    }
}
