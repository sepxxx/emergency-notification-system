package com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "lastname", "email", "tg", "token" })
@Table(name="recipients")
@FieldDefaults(level = AccessLevel.PRIVATE)
//:TODO обечпечение уникальности строк
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(name="lastname")
    String lastname;
    @Column(name="email")
    String email;
    @Column(name="tg")
    String tg;
    @Column(name="token")
    String token;

    @ManyToMany(mappedBy = "recipientList")
    List<RecipientList> recipientListNameList;
}
