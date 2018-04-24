package ru.sberbank.socialnetwork.users.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue
    long id;

    @Column(name = "nameRole", unique = true)
    String nameRole;

    public Role(String nameRole) {
        this.nameRole = nameRole;
    }

}