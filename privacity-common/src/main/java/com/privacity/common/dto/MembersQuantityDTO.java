package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@PrivacityIdExclude	
@AllArgsConstructor
@NoArgsConstructor
public class MembersQuantityDTO {
	@PrivacityIdExclude	
	private int quantityOnline;
	@PrivacityIdExclude	
	private int totalQuantity;
}
