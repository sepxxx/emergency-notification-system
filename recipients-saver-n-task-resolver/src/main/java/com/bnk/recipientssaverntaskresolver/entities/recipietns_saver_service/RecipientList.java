package com.bnk.recipientssaverntaskresolver.entities.recipietns_saver_service;


import com.bnk.recipientssaverntaskresolver.entities.User;
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
@Table(name="recipient_lists")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipientList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(name="list_name")
    String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="recipients_recipients_lists",
            joinColumns=@JoinColumn(name="recipient_list_id"),
            inverseJoinColumns = @JoinColumn(name="recipient_id"))
    List<Recipient> recipientList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public void appendRecipientList(List<Recipient> appendingRecipientList) {
        if(recipientList==null) {
            recipientList = new ArrayList<>();
        }
        recipientList.addAll(appendingRecipientList);
    }
}
