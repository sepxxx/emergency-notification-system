package com.bnk.recipientssaverntaskresolver.controllers;

import com.bnk.miscellaneous.entities.Recipient;
import com.bnk.recipientssaverntaskresolver.dtos.RecipientDto;
import com.bnk.recipientssaverntaskresolver.services.RecipientSaverService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CsvController {
    RecipientSaverService recipientSaverService;
//    UserRepository userRepository;


    @PostMapping("/upload")
    //
    public void uploadCSV(@RequestParam("file") MultipartFile file, @RequestParam String recipientsListName, @RequestParam Long userId) throws IOException {
//        log.info("uploadCSV file desc: {} {} {}", file.getName(),file.getOriginalFilename(),
//                file.getContentType());
        log.info("uploadCSV: file: {} recipientsListName: {} UserId:{}", file.getOriginalFilename(), recipientsListName, userId);




            CsvMapper csvMapper = new CsvMapper();
            CsvSchema csvSchema = csvMapper.schemaFor(RecipientDto.class)
                    .withColumnSeparator(',').withSkipFirstDataRow(true);
            Reader myReader = new InputStreamReader(file.getInputStream());

            MappingIterator<RecipientDto> iterator = csvMapper
                    .readerFor(RecipientDto.class)
                    .with(csvSchema)
                    .readValues(myReader);
            List<RecipientDto> elements = iterator.readAll();


            recipientSaverService.saveRecipients(elements, recipientsListName, userId);


    }
}
