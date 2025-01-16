package com.daengdaeng_eodiga.project.story.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStory is a Querydsl query type for Story
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStory extends EntityPathBase<Story> {

    private static final long serialVersionUID = -1541256949L;

    public static final QStory story = new QStory("story");

    public final StringPath city = createString("city");

    public final StringPath cityDetail = createString("cityDetail");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endAt = createDateTime("endAt", java.time.LocalDateTime.class);

    public final StringPath path = createString("path");

    public final NumberPath<Integer> storyId = createNumber("storyId", Integer.class);

    public final ListPath<StoryView, QStoryView> storyViews = this.<StoryView, QStoryView>createList("storyViews", StoryView.class, QStoryView.class, PathInits.DIRECT2);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QStory(String variable) {
        super(Story.class, forVariable(variable));
    }

    public QStory(Path<? extends Story> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStory(PathMetadata metadata) {
        super(Story.class, metadata);
    }

}

