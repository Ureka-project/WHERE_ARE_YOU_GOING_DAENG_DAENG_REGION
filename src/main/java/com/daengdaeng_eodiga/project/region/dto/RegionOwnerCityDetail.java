package com.daengdaeng_eodiga.project.region.dto;


import java.util.List;

import com.daengdaeng_eodiga.project.user.dto.PetInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class RegionOwnerCityDetail {
	private int userId;
	private String nickname;
	private int count;
	private List<PetInfo> pets;
}
