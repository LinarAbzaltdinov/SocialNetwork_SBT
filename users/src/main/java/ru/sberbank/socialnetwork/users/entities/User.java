package ru.sberbank.socialnetwork.users.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class User {
    @Id @GeneratedValue
    @Getter
    long id;
    @Column(name = "email", unique = true)
    @Getter @Setter
    String email;

    public User() {}

    public User(String email) {
        this.email = email;
    }
}
