package com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="recipient_list_names")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipientList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(name="list_name")
    String name;

    @OneToMany
    List<Recipient> recipientList;

    public void appendRecipientList(List<Recipient> appendingRecipientList) {
        if(recipientList==null) {
            recipientList = new ArrayList<>();
        }
        recipientList.addAll(appendingRecipientList);
    }
}
