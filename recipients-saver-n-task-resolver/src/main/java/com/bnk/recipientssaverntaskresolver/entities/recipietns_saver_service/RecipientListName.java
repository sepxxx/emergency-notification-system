package com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="recipient_list_names")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipientListName {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(name="list_name")
    String listName;

    @OneToMany
    List<Recipient> recipientList;
}
