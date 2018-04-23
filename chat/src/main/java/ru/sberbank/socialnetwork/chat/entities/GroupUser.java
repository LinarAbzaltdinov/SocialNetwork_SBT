package ru.sberbank.socialnetwork.chat.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "GROUP_USER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupUser {

    @Column(name="USER_ID", nullable=false)
    private String uuid;

    @Column(name = "ACCESS_MODE", nullable=false)
    private UserAccessMode accessMode;

    @ManyToOne
    @JoinColumn(name="GROUP_ID", nullable=false)
    private Group group;
}
