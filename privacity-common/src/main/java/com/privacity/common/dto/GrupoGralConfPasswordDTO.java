package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PrivacityIdExclude	
public class GrupoGralConfPasswordDTO {
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private boolean enabled;
	
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private boolean extraEncryptDefaultEnabled=true;
	
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private String password;
	
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private boolean deleteExtraEncryptEnabled=true;
	
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private String passwordExtraEncrypt;
	
	
		
}

