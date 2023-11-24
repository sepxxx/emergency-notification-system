package com.bnk.recipientssaverntaskresolver.dtos.responses;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
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
