package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = false, chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrivacityIdExclude	
public class AESAllDTO {
	@PrivacityIdExclude	
	private AESDTO sessionAESDTOServerIn;
	@PrivacityIdExclude	
	private AESDTO sessionAESDTOWS;
	@PrivacityIdExclude	
	private AESDTO sessionAESDTOServerOut;
}
