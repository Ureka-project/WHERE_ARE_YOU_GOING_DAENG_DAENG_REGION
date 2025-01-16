package com.daengdaeng_eodiga.project.region.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRegionVisitTotal is a Querydsl query type for RegionVisitTotal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegionVisitTotal extends EntityPathBase<RegionVisitTotal> {

    private static final long serialVersionUID = -42584506L;

    public static final QRegionVisitTotal regionVisitTotal = new QRegionVisitTotal("regionVisitTotal");

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

    public QRegionVisitTotal(String variable) {
        super(RegionVisitTotal.class, forVariable(variable));
    }

    public QRegionVisitTotal(Path<? extends RegionVisitTotal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRegionVisitTotal(PathMetadata metadata) {
        super(RegionVisitTotal.class, metadata);
    }

}

