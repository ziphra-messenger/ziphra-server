package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdDTO{
	@PrivacityId
	public String id;
	
	public IdDTO(Long id) {
		this.id=id+"";
		
	}
	
	public Long getIdLong() {
		return Long.parseLong(id);
	}

}
