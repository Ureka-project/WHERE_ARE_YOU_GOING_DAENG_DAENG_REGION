package com.daengdaeng_eodiga.project.story.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "story")
public class Story {

    @Id
    @Column(name = "story_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storyId;

    private int userId;

    @Column(name = "city")
    private String city;

    @Column(name = "city_detail")
    private String cityDetail;

    @Column(name = "path", length = 700)
    private String path;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "story", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoryView> storyViews = new ArrayList<>();

    @Builder
    public Story(int userId, String path, LocalDateTime createdAt, LocalDateTime endAt, String city, String cityDetail) {
        this.userId = userId;
        this.path = path;
        this.createdAt = createdAt;
        this.endAt = endAt;
        this.city = city;
        this.cityDetail = cityDetail;
    }
    public Story() {}
}