package com.bnk.recipientssaverntaskresolver.controllers;

import com.bnk.miscellaneous.entities.Recipient;
import com.bnk.recipientssaverntaskresolver.dtos.RecipientDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientssaverntaskresolver.services.RecipientSaverService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CsvController {
    RecipientSaverService recipientSaverService;



    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public RecipientListResponseDto uploadCSV(@RequestParam("file") MultipartFile file, @RequestParam String recipientsListName, Principal principal){
//        log.info("uploadCSV file desc: {} {} {}", file.getName(),file.getOriginalFilename(),
//                file.getContentType());



            return recipientSaverService.saveRecipients( file, recipientsListName, principal.getName());


    }
}
