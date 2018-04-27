package ru.sberbank.socialnetwork.chat.entities;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GROUPS")
@NoArgsConstructor
@Getter
@Setter
public class Group {

    @Id
    @Column(name = "GROUP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GROUP_NAME", nullable = false)
    private String groupName;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "ACCESS_MODE", nullable = false)
    private boolean isOpened;

    @OneToMany(mappedBy="group", fetch = FetchType.LAZY)
    private Set<Chat> chats;

    @OneToMany(mappedBy="group", fetch = FetchType.LAZY)
    private Set<GroupUser> users;

    @ElementCollection
    @CollectionTable(name = "GROUP_INVITED_USERS", joinColumns = @JoinColumn(name = "GROUP_ID"))
    @Column(name = "USER_ID")
    private Set<String> invitedUserUuids;

    public Group(String groupName, String description, boolean isOpened, String creatorId) {
        this.groupName = groupName;
        this.description = description;
        this.isOpened = isOpened;
        this.chats = new HashSet<>();
        this.users = new HashSet<>();
        this.invitedUserUuids = new HashSet<>();
    }
}
