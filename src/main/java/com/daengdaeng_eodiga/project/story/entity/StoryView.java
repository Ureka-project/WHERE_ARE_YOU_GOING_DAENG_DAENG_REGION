package com.daengdaeng_eodiga.project.story.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "story_view")
public class StoryView {

    @EmbeddedId
    private StoryViewId storyViewId;

    @MapsId("storyId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", insertable = false, updatable = false)
    private Story story;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public StoryView(StoryViewId storyViewId, Story story,LocalDateTime createdAt) {
        this.storyViewId = storyViewId;
        this.story = story;
        this.createdAt = createdAt;
    }
    public StoryView () {}
}