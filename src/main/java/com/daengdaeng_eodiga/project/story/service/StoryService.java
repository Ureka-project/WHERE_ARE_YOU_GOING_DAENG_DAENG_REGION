package com.daengdaeng_eodiga.project.story.service;

import com.daengdaeng_eodiga.project.Global.Redis.Repository.RedisStoryRepository;
import com.daengdaeng_eodiga.project.Global.exception.*;

import com.daengdaeng_eodiga.project.region.repository.RegionOwnerLogRepository;
import com.daengdaeng_eodiga.project.story.dto.*;
import com.daengdaeng_eodiga.project.story.entity.Story;

import com.daengdaeng_eodiga.project.story.entity.StoryView;
import com.daengdaeng_eodiga.project.story.entity.StoryViewId;
import com.daengdaeng_eodiga.project.story.repository.StoryRepository;
import com.daengdaeng_eodiga.project.story.repository.StoryViewRepository;
import com.daengdaeng_eodiga.project.user.dto.UserAndPetInfo;
import com.daengdaeng_eodiga.project.user.feignClient.UserFeignClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final StoryViewRepository storyViewRepository;
    private final RegionOwnerLogRepository regionOwnerLogRepository;
    private final RedisStoryRepository redisStoryRepository;
    private final UserFeignClient userFeignClient;

    /**
     * 스토리 업로드
     * @param userId
     * @param storyRequestDto
     */
    public void registerStory(int userId, StoryRequestDto storyRequestDto){
        if( storyRepository.countByTodayCreated(
                userId,
                LocalDate.now().atStartOfDay(),
                LocalDate.now().plusDays(1).atStartOfDay()) == 10 ) {
            throw new DailyStoryUploadLimitException();
        }
        if( regionOwnerLogRepository.findByUserIdAndCityAndCityDetailForUpload(
                userId,
                storyRequestDto.getCity(),
                storyRequestDto.getCityDetail()).isEmpty() ){
            throw new OwnerHistoryNotFoundException();
        }

        Story story = Story.builder()
                .userId(userId)
                .path(storyRequestDto.getPath())
                .city(storyRequestDto.getCity())
                .cityDetail(storyRequestDto.getCityDetail())
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusHours(24))
                .build();

        storyRepository.save(story);

        UserAndPetInfo userAndPetInfo = userFeignClient.fetchUserAndPetsInfo(userId);
        String petImage = !userAndPetInfo.pets().isEmpty() ? null : userAndPetInfo.pets().get(0).petImg();

        RedisGroupedUserStoriesDto redisStory = RedisGroupedUserStoriesDto.builder()
                .landOwnerId(userId)
                .storyId(story.getStoryId())
                .nickname(userAndPetInfo.userName())
                .city(storyRequestDto.getCity())
                .cityDetail(storyRequestDto.getCityDetail())
                .petImage(petImage)
                .storyType("unviewed")
                .build();
        redisStoryRepository.saveStory(storyRequestDto.getCity(), storyRequestDto.getCityDetail(), userId, story.getStoryId(), redisStory);
    }

    /**
     * 본인 제외 전체 유저 스토리 목록 조회
     * @param userId
     * @return
     */
    public List<GroupedUserStoriesDto> fetchGroupedUserStories(int userId){
        List<Object[]> results = storyRepository.findMainPriorityStories(userId);

        return results.stream()
                .map(row -> GroupedUserStoriesDto.builder()
                        .landOwnerId((Integer) row[0])
                        .nickname((String) row[1])
                        .city((String) row[2])
                        .cityDetail((String) row[3])
                        .petImage((String) row[4])
                        .storyType((String) row[5])
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 비회원 전용 전체 유저 스토리 목록 조회
     * @param
     * @return
     */
    public List<RedisGroupedUserStoriesDto> fetchGroupedUserStoriesForNotUser(){
        List<RedisGroupedUserStoriesDto> cachedStories = redisStoryRepository.getAllStories();

        if( cachedStories == null || cachedStories.isEmpty() ){
            List<Story> stories = storyRepository.findByEndAtAfter(LocalDateTime.now());
            for (Story story : stories) {
                UserAndPetInfo userAndPetInfo = userFeignClient.fetchUserAndPetsInfo(story.getUserId());
                String petImage = !userAndPetInfo.pets().isEmpty() ? null : userAndPetInfo.pets().get(0).petImg();

                RedisGroupedUserStoriesDto redisStory = RedisGroupedUserStoriesDto.builder()
                        .landOwnerId(story.getUserId())
                        .storyId(story.getStoryId())
                        .nickname(userAndPetInfo.userName())
                        .city(story.getCity())
                        .cityDetail(story.getCityDetail())
                        .petImage(petImage)
                        .storyType("unviewed")
                        .build();

                redisStoryRepository.saveStory(story.getCity(), story.getCityDetail(), story.getUserId(),
                        story.getStoryId(), redisStory);
                cachedStories.add(redisStory);
            }
        }

        return cachedStories.stream()
                .collect(Collectors.groupingBy(story -> Arrays.asList(
                        story.getLandOwnerId(),
                        story.getCity(),
                        story.getCityDetail()
                )))
                .entrySet().stream()
                .map(entry -> {

                    List<RedisGroupedUserStoriesDto> groupedStories = entry.getValue();
                    RedisGroupedUserStoriesDto representativeStory = groupedStories.stream()
                            .min(Comparator.comparingInt(RedisGroupedUserStoriesDto::getStoryId))
                            .orElseThrow();

                    return representativeStory;
                })
                .sorted(Comparator.comparing(RedisGroupedUserStoriesDto::getStoryId).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 내 스토리 목록 조회
     * @param userId
     * @return
     */
    public MyStoriesDto fetchMyStories(int userId) {
        List<Story> results = storyRepository.findMyActiveStoriesByUserId(userId);
        if( results.isEmpty() ) {
            throw new UserStoryNotFoundException();
        }

        UserAndPetInfo userAndPetInfo = userFeignClient.fetchUserAndPetsInfo(userId);
        String nickname = userAndPetInfo.userName();
        List<MyStoryContentDto> content = results.stream()
                .map(row -> MyStoryContentDto.builder()
                        .storyId(row.getStoryId())
                        .city(row.getCity())
                        .cityDetail(row.getCityDetail())
                        .path(row.getPath())
                        .build())
                .collect(Collectors.toList());

        return MyStoriesDto.builder()
                .nickname(nickname)
                .content(content)
                .build();
    }

    /**
     * 유저별 스토리 상세목록 조회
     * @param landOwnerId
     * @param city
     * @param cityDetail
     * @return
     */
    public IndividualUserStoriesDto fetchIndividualUserStories(int landOwnerId, String city, String cityDetail){
        List<Story> results = storyRepository.findActiveStoriesByLandOwnerId(landOwnerId, city, cityDetail);
        if( results.isEmpty() ) {
            throw new UserStoryNotFoundException();
        }

        UserAndPetInfo userAndPetInfo = userFeignClient.fetchUserAndPetsInfo(results.get(0).getUserId());
        String nickname = userAndPetInfo.userName();

        List<IndividualStoryContentDto> content = results.stream()
                .map(row -> IndividualStoryContentDto.builder()
                        .storyId(row.getStoryId())
                        .path(row.getPath())
                        .build())
                .collect(Collectors.toList());

        return IndividualUserStoriesDto.builder()
                .nickname(nickname)
                .city(city)
                .cityDetail(cityDetail)
                .content(content)
                .build();
    }

    /**
     * 스토리 확인
     * @param storyId
     * @param userId
     */
    public void viewStory(int storyId, int userId){
        StoryViewId storyViewId = StoryViewId.builder()
                .userId(userId)
                .storyId(storyId)
                .build();
        Story story = storyRepository.findByStoryId(storyId).orElseThrow(UserStoryNotFoundException::new);

        StoryView storyView = StoryView.builder()
                .storyViewId(storyViewId)
                .story(story)
                .createdAt(LocalDateTime.now())
                .build();
        storyViewRepository.save(storyView);
    }

    /**
     * 스토리 삭제
     * @param storyId
     */
    public void deleteStory(int storyId){
        Story story = storyRepository.findByStoryId(storyId).orElseThrow(UserStoryNotFoundException::new);
        storyRepository.deleteById(storyId);
        redisStoryRepository.deleteStory(story.getCity(), story.getCityDetail(), story.getUserId(), story.getStoryId());
    }
}