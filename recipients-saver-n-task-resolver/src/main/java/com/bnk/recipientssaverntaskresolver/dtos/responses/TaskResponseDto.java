package com.bnk.recipientssaverntaskresolver.dtos.responses;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskResponseDto {
    Long id;
    String recipientListName;
    String text;
    Long done;
    Long total;
}
