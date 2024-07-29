package com.housekeeping.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = 1906179498L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final ListPath<com.housekeeping.entity.ChatRoomMember, com.housekeeping.entity.QChatRoomMember> chatRoomMembers = this.<com.housekeeping.entity.ChatRoomMember, com.housekeeping.entity.QChatRoomMember>createList("chatRoomMembers", com.housekeeping.entity.ChatRoomMember.class, com.housekeeping.entity.QChatRoomMember.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.Cloth, com.housekeeping.entity.QCloth> cloths = this.<com.housekeeping.entity.Cloth, com.housekeeping.entity.QCloth>createList("cloths", com.housekeeping.entity.Cloth.class, com.housekeeping.entity.QCloth.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.Comment, com.housekeeping.entity.QComment> comments = this.<com.housekeeping.entity.Comment, com.housekeeping.entity.QComment>createList("comments", com.housekeeping.entity.Comment.class, com.housekeeping.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<com.housekeeping.entity.Food, com.housekeeping.entity.QFood> foods = this.<com.housekeeping.entity.Food, com.housekeeping.entity.QFood>createList("foods", com.housekeeping.entity.Food.class, com.housekeeping.entity.QFood.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.Friend, com.housekeeping.entity.QFriend> friends1 = this.<com.housekeeping.entity.Friend, com.housekeeping.entity.QFriend>createList("friends1", com.housekeeping.entity.Friend.class, com.housekeeping.entity.QFriend.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.Friend, com.housekeeping.entity.QFriend> friends2 = this.<com.housekeeping.entity.Friend, com.housekeeping.entity.QFriend>createList("friends2", com.housekeeping.entity.Friend.class, com.housekeeping.entity.QFriend.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.Guestbook, com.housekeeping.entity.QGuestbook> guestbookEntriesOwned = this.<com.housekeeping.entity.Guestbook, com.housekeeping.entity.QGuestbook>createList("guestbookEntriesOwned", com.housekeeping.entity.Guestbook.class, com.housekeeping.entity.QGuestbook.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.Guestbook, com.housekeeping.entity.QGuestbook> guestbookEntriesWritten = this.<com.housekeeping.entity.Guestbook, com.housekeeping.entity.QGuestbook>createList("guestbookEntriesWritten", com.housekeeping.entity.Guestbook.class, com.housekeeping.entity.QGuestbook.class, PathInits.DIRECT2);

    public final com.housekeeping.entity.QLevelEXPTable level;

    public final ListPath<com.housekeeping.entity.MessageReadStatus, com.housekeeping.entity.QMessageReadStatus> messageReadStatuses = this.<com.housekeeping.entity.MessageReadStatus, com.housekeeping.entity.QMessageReadStatus>createList("messageReadStatuses", com.housekeeping.entity.MessageReadStatus.class, com.housekeeping.entity.QMessageReadStatus.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.Message, com.housekeeping.entity.QMessage> messages = this.<com.housekeeping.entity.Message, com.housekeeping.entity.QMessage>createList("messages", com.housekeeping.entity.Message.class, com.housekeeping.entity.QMessage.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath provider = createString("provider");

    public final StringPath providerId = createString("providerId");

    public final ListPath<com.housekeeping.entity.FriendRequest, com.housekeeping.entity.QFriendRequest> receivedFriendRequests = this.<com.housekeeping.entity.FriendRequest, com.housekeeping.entity.QFriendRequest>createList("receivedFriendRequests", com.housekeeping.entity.FriendRequest.class, com.housekeeping.entity.QFriendRequest.class, PathInits.DIRECT2);

    public final StringPath role = createString("role");

    public final ListPath<com.housekeeping.entity.Room, com.housekeeping.entity.QRoom> rooms = this.<com.housekeeping.entity.Room, com.housekeeping.entity.QRoom>createList("rooms", com.housekeeping.entity.Room.class, com.housekeeping.entity.QRoom.class, PathInits.DIRECT2);

    public final ListPath<com.housekeeping.entity.FriendRequest, com.housekeeping.entity.QFriendRequest> sentFriendRequests = this.<com.housekeeping.entity.FriendRequest, com.housekeeping.entity.QFriendRequest>createList("sentFriendRequests", com.housekeeping.entity.FriendRequest.class, com.housekeeping.entity.QFriendRequest.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final BooleanPath userIsOnline = createBoolean("userIsOnline");

    public QUserEntity(String variable) {
        this(UserEntity.class, forVariable(variable), INITS);
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserEntity(PathMetadata metadata, PathInits inits) {
        this(UserEntity.class, metadata, inits);
    }

    public QUserEntity(Class<? extends UserEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.level = inits.isInitialized("level") ? new com.housekeeping.entity.QLevelEXPTable(forProperty("level")) : null;
    }

}

