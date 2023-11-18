package com.bnk.miscellaneous.entities;



import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//@JsonPropertyOrder({ "lastname", "email", "tg", "token" })
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
    Set<RecipientList> recipientListNameList;
}
