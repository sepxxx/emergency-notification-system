package com.bnk.recipientssaverntaskresolver.repositories;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.RecipientList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipientListNameRepository extends
        JpaRepository<RecipientList, Long> {
//    List<Recipient> findAllRecipientsByName(String recipientListName);
    Optional<RecipientList> findByName(String recipientListName);
}
