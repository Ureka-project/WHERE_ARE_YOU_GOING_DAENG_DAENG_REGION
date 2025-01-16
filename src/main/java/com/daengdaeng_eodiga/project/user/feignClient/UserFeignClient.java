package com.daengdaeng_eodiga.project.user.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.daengdaeng_eodiga.project.Global.dto.ApiResponse;
import com.daengdaeng_eodiga.project.user.dto.UserAndPetInfo;

@FeignClient(name = "project-service", path = "/api/v2/user-service")
public interface UserFeignClient {

	@GetMapping("/user-pets/{userId}")
	public UserAndPetInfo fetchUserAndPetsInfo(@PathVariable int userId);

}
