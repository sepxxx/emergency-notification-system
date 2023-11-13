package com.bnk.recipientssaverntaskresolver.controllers;

import com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service.Recipient;
import com.bnk.recipientssaverntaskresolver.services.RecipientSaverService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class CsvController {
    RecipientSaverService recipientSaverService;

    @PostMapping("/upload")
    public void uploadCSV(@RequestParam("file") MultipartFile file) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Recipient.class)
                .withColumnSeparator(',').withSkipFirstDataRow(true);
        Reader myReader = new InputStreamReader(file.getInputStream());

        MappingIterator<Recipient> iterator = csvMapper
                .readerFor(Recipient.class)
                .with(csvSchema)
                .readValues(myReader);
        List<Recipient> elements = iterator.readAll();

        System.out.println(elements.size());
        recipientSaverService.saveRecipients(elements);
//        return elements;
    }
}
