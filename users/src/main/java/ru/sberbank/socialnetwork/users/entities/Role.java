package ru.sberbank.socialnetwork.users.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    long id;

    @Column(name = "nameRole", unique = true)
    @Getter
    @Setter
    String nameRole;

    public Role() {
    }

    public Role(String nameRole) {
        this.nameRole = nameRole;
    }

}
