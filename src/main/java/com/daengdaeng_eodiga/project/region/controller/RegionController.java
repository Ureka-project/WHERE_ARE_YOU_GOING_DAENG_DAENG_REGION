package com.daengdaeng_eodiga.project.region.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daengdaeng_eodiga.project.Global.dto.ApiResponse;
import com.daengdaeng_eodiga.project.region.dto.RegionVisit;
import com.daengdaeng_eodiga.project.region.dto.VisitRegionRequest;
import com.daengdaeng_eodiga.project.region.dto.VisitRegionResponse;
import com.daengdaeng_eodiga.project.region.service.RegionService;
import com.daengdaeng_eodiga.project.story.dto.UserMyLandsDto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/region")
@RequiredArgsConstructor
public class RegionController {

	private final RegionService regionService;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final KafkaTemplate<String, Object> kafkaTemplate;

	@GetMapping("/owners")
	public ResponseEntity<ApiResponse<RegionVisit>> fetchRegionOwner(){
		RegionVisit response = regionService.fetchRegionOwners();
		return ResponseEntity.ok(ApiResponse.success(response) );
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<UserMyLandsDto>> fetchUserRegion(
		@RequestHeader("userId") String userIdHeader
	){
		int userId = Integer.parseInt(userIdHeader);
		UserMyLandsDto response = regionService.fetchUserLands(userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@GetMapping("/visitCount")
	public ResponseEntity<ApiResponse<RegionVisit>> fetchUserRegionVisitCount(
		@RequestHeader("userId") String userIdHeader
	){
		int userId = Integer.parseInt(userIdHeader);
		RegionVisit response = regionService.fetchUserCityDetailVisitCountForDB(userId);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@KafkaListener(topics = "add_visitRegion", groupId = "region_visit")
	public void addRegionVisitListener(ConsumerRecord data) throws JsonProcessingException {
		VisitRegionRequest visitRegionRequest = objectMapper.readValue((String)data.value(), VisitRegionRequest.class);
		try {
			regionService.addCountVisitRegionForDB(visitRegionRequest.city(), visitRegionRequest.cityDetail(), visitRegionRequest.userId());
		} catch (Exception e) {
			VisitRegionResponse failResponse = new VisitRegionResponse(false, visitRegionRequest.reviewId());
			kafkaTemplate.send("add_visitRegion_transaction_response", failResponse);
		}
	}

}
