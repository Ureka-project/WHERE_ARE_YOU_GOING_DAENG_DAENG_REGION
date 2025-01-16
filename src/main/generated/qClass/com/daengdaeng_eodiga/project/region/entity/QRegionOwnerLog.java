package com.daengdaeng_eodiga.project.region.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegionOwnerLog is a Querydsl query type for RegionOwnerLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegionOwnerLog extends EntityPathBase<RegionOwnerLog> {

    private static final long serialVersionUID = -364029858L;

    public static final QRegionOwnerLog regionOwnerLog = new QRegionOwnerLog("regionOwnerLog");

    public final com.daengdaeng_eodiga.project.Global.entity.QBaseEntity _super = new com.daengdaeng_eodiga.project.Global.entity.QBaseEntity(this);

    public final StringPath city = createString("city");

    public final StringPath cityDetail = createString("cityDetail");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QRegionOwnerLog(String variable) {
        super(RegionOwnerLog.class, forVariable(variable));
    }

    public QRegionOwnerLog(Path<? extends RegionOwnerLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegionOwnerLog(PathMetadata metadata) {
        super(RegionOwnerLog.class, metadata);
    }

}

