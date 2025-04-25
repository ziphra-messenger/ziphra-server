package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.CUSTOM)
@PrivacityIdExclude	
public class LockDTO {
	@JsonInclude(Include.NON_DEFAULT)
	@PrivacityIdExclude	
	private boolean enabled;
	
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private int seconds;
	
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private int minSecondsValidation;


	
}
