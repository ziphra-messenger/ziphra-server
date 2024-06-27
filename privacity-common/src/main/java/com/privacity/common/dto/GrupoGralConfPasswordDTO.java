package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoGralConfPasswordDTO {
	@JsonInclude(Include.NON_NULL)
	public boolean enabled;
	
	@JsonInclude(Include.NON_NULL)
	public boolean extraEncryptDefaultEnabled=true;
	
	@JsonInclude(Include.NON_NULL)
	public String password;
	
	@JsonInclude(Include.NON_NULL)
	public boolean deleteExtraEncryptEnabled=true;
	
	@JsonInclude(Include.NON_NULL)
	public String passwordExtraEncrypt;
	
	
		
}

