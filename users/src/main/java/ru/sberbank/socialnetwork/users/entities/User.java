package ru.sberbank.socialnetwork.users.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "uuid")
@NoArgsConstructor

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "email", nullable = false, unique = true) //email == login
    private String email;

    @Column(name = "password")
    //@Transient
    //@JsonIgnore
    private String password;

    @Column(name = "firstName")
    private String firstName = "";

    @Column(name = "lastName")
    private String lastName = "";

    @Column(name = "photoId")
    private String photoId;

    @Column(name = "sex")
    private int sex;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;

    public void update(User updatedUser) {
        if (updatedUser.firstName != null) {
            this.firstName = updatedUser.firstName;
        }
        if (updatedUser.lastName != null) {
            this.lastName = updatedUser.lastName;
        }
        if (updatedUser.password != null) {
            this.password = updatedUser.password;
        }
        if (updatedUser.birthday != null) {
            this.birthday = updatedUser.birthday;
        }
        if (updatedUser.photoId != null) {
            this.photoId = updatedUser.photoId;
        }
        if (updatedUser.sex != 0) {
            this.sex = updatedUser.sex;
        }
    }
}
