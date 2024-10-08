package com.housekeeping.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.housekeeping.entity.enums.UserPlatform;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "userPlatform"}),
                @UniqueConstraint(columnNames = {"nickname"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserPlatform userPlatform;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime userEnrollment = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "levelId", nullable = false)
    private LevelEXPTable level;

    @Column(nullable = false)
    private int userEXP = 0;

    @Column
    private String profileImageUrl;

    @Column(nullable = false)
    private boolean userIsOnline = false;

    private String phoneNumber;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "messageSender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatRoomMember> chatRoomMembers;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MessageReadStatus> messageReadStatuses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cloth> cloths;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Food> foods;

    @OneToMany(mappedBy = "friendUser1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Friend> friends1;

    @OneToMany(mappedBy = "friendUser2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Friend> friends2;

    @OneToMany(mappedBy = "requestSender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FriendRequest> sentFriendRequests;

    @OneToMany(mappedBy = "requestReceiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FriendRequest> receivedFriendRequests;

    @OneToMany(mappedBy = "guestbookOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Guestbook> guestbookEntriesOwned;

    @OneToMany(mappedBy = "guestbookWriter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Guestbook> guestbookEntriesWritten;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // 순환 참조 방지
    private List<Room> rooms;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<AdminTip> posts;
}
