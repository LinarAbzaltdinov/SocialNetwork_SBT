package ru.sberbank.socialnetwork.chat.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "CHAT")
@NoArgsConstructor
@Getter
@Setter
public class Chat {

    @Id
    @Column(name = "CHAT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CHAT_NAME", nullable = false)
    private String chatName;

    @Column(name = "CREATOR_ID", nullable = false)
    private String creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GROUP_ID", nullable=false)
    private Group group;

    @Column(name = "IS_OPENED", nullable = false)
    private boolean isOpened;

    @ElementCollection
    @CollectionTable(name = "CHAT_USERS", joinColumns = @JoinColumn(name = "CHAT_ID"))
    @Column(name = "USER_ID")
    private Set<String> userUuids;


    public Chat(String creatorId, String chatName, boolean isOpened, Group group) {
        this.creatorId = creatorId;
        this.chatName = chatName;
        this.isOpened = isOpened;
        userUuids = new HashSet<>();
        userUuids.add(creatorId);
        this.group = group;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chatName='" + chatName + '\'' +
                ", creatorId='" + creatorId + '\'' +
                '}';
    }
}
