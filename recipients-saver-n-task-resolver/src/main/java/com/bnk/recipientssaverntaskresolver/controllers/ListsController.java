package com.bnk.recipientssaverntaskresolver.controllers;


import com.bnk.recipientssaverntaskresolver.services.RecipientListNameService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/list")
public class ListsController {
    RecipientListNameService recipientListNameService;

    @GetMapping("/recipients")
    public List<String> getAllListNamesUserHave(Principal principal){
        return recipientListNameService.getAllListNamesUserHave(principal.getName());
    }

}
