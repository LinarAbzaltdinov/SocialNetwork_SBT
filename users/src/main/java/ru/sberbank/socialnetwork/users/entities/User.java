package ru.sberbank.socialnetwork.users.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class User {
    @Id @GeneratedValue
    @Getter
    long id;
    @Column()
    @Getter @Setter
    String email;

    public User(String email) {
        this.email = email;
    }
}
