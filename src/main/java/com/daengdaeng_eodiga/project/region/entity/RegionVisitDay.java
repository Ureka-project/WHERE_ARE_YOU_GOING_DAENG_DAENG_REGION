package com.daengdaeng_eodiga.project.region.entity;
import jakarta.persistence.*;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.daengdaeng_eodiga.project.Global.entity.BaseEntity;


@Entity
@Table(name = "Region_visit_day")
@Getter
@NoArgsConstructor
public class RegionVisitDay extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "city")
	private String city;

	@Column(name = "city_detail")
	private String cityDetail;

	@Column(name = "count")
	private int count;

	@Column(name = "user_id")
	private int userId;

	@Builder
	public RegionVisitDay(int userId, String city, String cityDetail, int count) {
		this.city = city;
		this.cityDetail = cityDetail;
		this.count = count;
		this.userId = userId;
	}

	public void addCount() {
		this.count +=1;
	}

	public void decrementCount() {
		this.count -=1;
	}
}
