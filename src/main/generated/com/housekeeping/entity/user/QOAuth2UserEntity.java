package com.housekeeping.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOAuth2UserEntity is a Querydsl query type for OAuth2UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOAuth2UserEntity extends EntityPathBase<OAuth2UserEntity> {

    private static final long serialVersionUID = 652817253L;

    public static final QOAuth2UserEntity oAuth2UserEntity = new QOAuth2UserEntity("oAuth2UserEntity");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath role = createString("role");

    public final StringPath username = createString("username");

    public QOAuth2UserEntity(String variable) {
        super(OAuth2UserEntity.class, forVariable(variable));
    }

    public QOAuth2UserEntity(Path<? extends OAuth2UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOAuth2UserEntity(PathMetadata metadata) {
        super(OAuth2UserEntity.class, metadata);
    }

}

