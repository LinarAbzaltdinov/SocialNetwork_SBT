package ru.sberbank.socialnetwork.chat.entities;

import lombok.*;

import javax.persistence.*;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "CHAT")
@Getter
@Setter
@ToString
public class Chat {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "CHAT_NAME")
    private String chatName;

    @NonNull
    @Column(name = "CREATOR_ID")
    private String creatorId;

    @ElementCollection
    @CollectionTable(name = "CHAT_MESSAGES", joinColumns = @JoinColumn(name = "CHAT_ID"))
    @Column(name = "MESSAGE")
    private SortedSet<String> messages;

    @ElementCollection
    @CollectionTable(name = "CHAT_USERS", joinColumns = @JoinColumn(name = "CHAT_ID"))
    @Column(name = "USER")
    private SortedSet<String> users;

    public Chat(String creatorId, String chatName) {
        this.creatorId = creatorId;
        this.chatName = chatName;
        users = new TreeSet<String>();
        users.add(creatorId);
    }
}

