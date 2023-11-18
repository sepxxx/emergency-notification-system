package com.bnk.recipientssaverntaskresolver.repositories;


import com.bnk.miscellaneous.entities.RecipientList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipientListNameRepository extends
        JpaRepository<RecipientList, Long> {
//    List<Recipient> findAllRecipientsByName(String recipientListName);
    Optional<RecipientList> findByName(String recipientListName);
}
