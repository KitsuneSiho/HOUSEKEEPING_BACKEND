package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 691137938L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<ChatRoomMember, QChatRoomMember> chatRoomMembers = this.<ChatRoomMember, QChatRoomMember>createList("chatRoomMembers", ChatRoomMember.class, QChatRoomMember.class, PathInits.DIRECT2);

    public final ListPath<Cloth, QCloth> cloths = this.<Cloth, QCloth>createList("cloths", Cloth.class, QCloth.class, PathInits.DIRECT2);

    public final ListPath<Comment, QComment> comments = this.<Comment, QComment>createList("comments", Comment.class, QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<Food, QFood> foods = this.<Food, QFood>createList("foods", Food.class, QFood.class, PathInits.DIRECT2);

    public final ListPath<Friend, QFriend> friends1 = this.<Friend, QFriend>createList("friends1", Friend.class, QFriend.class, PathInits.DIRECT2);

    public final ListPath<Friend, QFriend> friends2 = this.<Friend, QFriend>createList("friends2", Friend.class, QFriend.class, PathInits.DIRECT2);

    public final ListPath<Guestbook, QGuestbook> guestbookEntriesOwned = this.<Guestbook, QGuestbook>createList("guestbookEntriesOwned", Guestbook.class, QGuestbook.class, PathInits.DIRECT2);

    public final ListPath<Guestbook, QGuestbook> guestbookEntriesWritten = this.<Guestbook, QGuestbook>createList("guestbookEntriesWritten", Guestbook.class, QGuestbook.class, PathInits.DIRECT2);

    public final QLevelEXPTable level;

    public final ListPath<MessageReadStatus, QMessageReadStatus> messageReadStatuses = this.<MessageReadStatus, QMessageReadStatus>createList("messageReadStatuses", MessageReadStatus.class, QMessageReadStatus.class, PathInits.DIRECT2);

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<FriendRequest, QFriendRequest> receivedFriendRequests = this.<FriendRequest, QFriendRequest>createList("receivedFriendRequests", FriendRequest.class, QFriendRequest.class, PathInits.DIRECT2);

    public final EnumPath<com.housekeeping.entity.enums.Role> role = createEnum("role", com.housekeeping.entity.enums.Role.class);

    public final ListPath<Room, QRoom> rooms = this.<Room, QRoom>createList("rooms", Room.class, QRoom.class, PathInits.DIRECT2);

    public final ListPath<FriendRequest, QFriendRequest> sentFriendRequests = this.<FriendRequest, QFriendRequest>createList("sentFriendRequests", FriendRequest.class, QFriendRequest.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> userEnrollment = createDateTime("userEnrollment", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userEXP = createNumber("userEXP", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final ArrayPath<byte[], Byte> userImage = createArray("userImage", byte[].class);

    public final BooleanPath userIsOnline = createBoolean("userIsOnline");

    public final StringPath username = createString("username");

    public final EnumPath<com.housekeeping.entity.enums.UserPlatform> userPlatform = createEnum("userPlatform", com.housekeeping.entity.enums.UserPlatform.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.level = inits.isInitialized("level") ? new QLevelEXPTable(forProperty("level")) : null;
    }

}

