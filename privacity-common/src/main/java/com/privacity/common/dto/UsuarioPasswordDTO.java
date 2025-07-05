package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UsuarioPasswordDTO {
	@PrivacityIdExclude	
	private String username;
	@PrivacityIdExclude	
	private String password;
}
