package ru.sberbank.socialnetwork.chat.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "GROUP_USER")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class GroupUser {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NonNull
    @Column(name="USER_ID", nullable=false)
    private String uuid;

    @NonNull
    @Column(name = "ACCESS_MODE", nullable=false)
    private UserAccessMode accessMode;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GROUP_ID", nullable=false)
    private Group group;
}
