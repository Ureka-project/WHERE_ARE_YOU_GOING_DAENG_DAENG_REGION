package com.daengdaeng_eodiga.project.region.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegionVisitDay is a Querydsl query type for RegionVisitDay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegionVisitDay extends EntityPathBase<RegionVisitDay> {

    private static final long serialVersionUID = -442517730L;

    public static final QRegionVisitDay regionVisitDay = new QRegionVisitDay("regionVisitDay");

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

    public QRegionVisitDay(String variable) {
        super(RegionVisitDay.class, forVariable(variable));
    }

    public QRegionVisitDay(Path<? extends RegionVisitDay> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegionVisitDay(PathMetadata metadata) {
        super(RegionVisitDay.class, metadata);
    }

}

