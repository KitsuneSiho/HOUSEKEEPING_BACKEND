package com.housekeeping.entity.user;

import com.housekeeping.entity.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "provider"}),
                @UniqueConstraint(columnNames = {"nickname"})
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"level", "messages", "chatRoomMembers", "messageReadStatuses", "cloths", "comments", "foods", "friends1", "friends2", "sentFriendRequests", "receivedFriendRequests", "guestbookEntriesOwned", "guestbookEntriesWritten", "rooms"})
@EqualsAndHashCode(exclude = {"level", "messages", "chatRoomMembers", "messageReadStatuses", "cloths", "comments", "foods", "friends1", "friends2", "sentFriendRequests", "receivedFriendRequests", "guestbookEntriesOwned", "guestbookEntriesWritten", "rooms"})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String phoneNumber;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private LevelEXPTable level;

    @OneToMany(mappedBy = "messageSender", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatRoomMember> chatRoomMembers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MessageReadStatus> messageReadStatuses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cloth> cloths;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Food> foods;

    @OneToMany(mappedBy = "friendUser1", cascade = CascadeType.ALL)
    private List<Friend> friends1;

    @OneToMany(mappedBy = "friendUser2", cascade = CascadeType.ALL)
    private List<Friend> friends2;

    @OneToMany(mappedBy = "requestSender", cascade = CascadeType.ALL)
    private List<FriendRequest> sentFriendRequests;

    @OneToMany(mappedBy = "requestReceiver", cascade = CascadeType.ALL)
    private List<FriendRequest> receivedFriendRequests;

    @OneToMany(mappedBy = "guestbookOwner", cascade = CascadeType.ALL)
    private List<Guestbook> guestbookEntriesOwned;

    @OneToMany(mappedBy = "guestbookWriter", cascade = CascadeType.ALL)
    private List<Guestbook> guestbookEntriesWritten;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @Column(nullable = false)
    private boolean userIsOnline = false;
}