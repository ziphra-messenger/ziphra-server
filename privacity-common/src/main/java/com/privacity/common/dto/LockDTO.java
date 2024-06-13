package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class LockDTO {
	@JsonInclude(Include.NON_NULL)
	public boolean enabled;
	@JsonInclude(Include.NON_NULL)
	public Integer seconds;
	@JsonInclude(Include.NON_NULL)
	public Integer minSecondsValidation;


	
}
