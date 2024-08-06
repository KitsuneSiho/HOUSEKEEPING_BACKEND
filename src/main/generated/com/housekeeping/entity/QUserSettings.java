package com.housekeeping.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSettings is a Querydsl query type for UserSettings
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserSettings extends EntityPathBase<UserSettings> {

    private static final long serialVersionUID = -1420207147L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSettings userSettings = new QUserSettings("userSettings");

    public final BooleanPath settingCheckNotice = createBoolean("settingCheckNotice");

    public final BooleanPath settingFoodNotice = createBoolean("settingFoodNotice");

    public final BooleanPath settingIsOfflineMode = createBoolean("settingIsOfflineMode");

    public final BooleanPath settingSearchAgree = createBoolean("settingSearchAgree");

    public final BooleanPath settingWarnNotice = createBoolean("settingWarnNotice");

    public final QUser user;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserSettings(String variable) {
        this(UserSettings.class, forVariable(variable), INITS);
    }

    public QUserSettings(Path<? extends UserSettings> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSettings(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSettings(PathMetadata metadata, PathInits inits) {
        this(UserSettings.class, metadata, inits);
    }

    public QUserSettings(Class<? extends UserSettings> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

