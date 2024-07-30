package com.housekeeping.entity.user;

import com.housekeeping.entity.*;
import com.housekeeping.entity.enums.Role;
import com.housekeeping.entity.enums.UserPlatform;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email", "provider"}),
                @UniqueConstraint(columnNames = {"nickname"})
        })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "provider", nullable = false)
    private UserPlatform userPlatform;

    @Column(nullable = false)
    private LocalDateTime userEnrollment;

    @ManyToOne
    @JoinColumn(name = "levelId", nullable = false)
    private LevelEXPTable level;

    @Column(nullable = false)
    private int userEXP = 0;

    @Lob
    private byte[] userImage;

    @Column(nullable = false)
    private boolean userIsOnline = false;

    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String providerId;

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

    @PrePersist
    protected void onCreate() {
        userEnrollment = LocalDateTime.now();
    }
}
