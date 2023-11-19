package com.bnk.recipientssaverntaskresolver.services;

import com.bnk.miscellaneous.entities.Recipient;
import com.bnk.miscellaneous.entities.RecipientList;
import com.bnk.miscellaneous.entities.User;
import com.bnk.recipientssaverntaskresolver.dtos.RecipientDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.RecipientListResponseDto;
import com.bnk.recipientssaverntaskresolver.exceptions.NotFoundException;
import com.bnk.recipientssaverntaskresolver.repositories.RecipientListNameRepository;
import com.bnk.recipientssaverntaskresolver.repositories.RecipientRepository;
import com.bnk.recipientssaverntaskresolver.repositories.UserRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecipientSaverService {
    RecipientRepository recipientRepository;
    RecipientListNameRepository recipientListNameRepository;
    UserRepository userRepository;

    @SneakyThrows
    @Transactional
    public RecipientListResponseDto saveRecipients(MultipartFile file, String recipientsListName, String currentUsername) {
        log.info("uploadCSV: file: {} recipientsListName: {} currentUsername:{}", file.getOriginalFilename(), recipientsListName, currentUsername);
        List<RecipientDto>  recipientDtosWithoutIds = parseCsv(file);
        List<Recipient> recipientsWithIds = recipientRepository
                .saveAll(recipientDtosWithoutIds.stream().map(recipientDto ->
                                new Recipient(
                recipientDto.getLastname(),
                recipientDto.getEmail(),
                recipientDto.getTg(),
                recipientDto.getToken())
                ).collect(Collectors.toSet()));

        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NotFoundException("User not found"));

        RecipientList recipientListWithId = recipientListNameRepository.findByName(recipientsListName)
                .orElseGet(() -> recipientListNameRepository
                .save(new RecipientList(recipientsListName, user)));

        recipientListWithId.appendRecipientList(recipientsWithIds);

        return new RecipientListResponseDto(recipientListWithId.getId(),
                recipientsListName, recipientListWithId.getRecipientList().size());
    }

    private List<RecipientDto>  parseCsv(MultipartFile file) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(RecipientDto.class)
                .withColumnSeparator(',').withSkipFirstDataRow(true);
        Reader myReader = new InputStreamReader(file.getInputStream());

        MappingIterator<RecipientDto> iterator = csvMapper
                .readerFor(RecipientDto.class)
                .with(csvSchema)
                .readValues(myReader);
        return iterator.readAll();
    }
}
