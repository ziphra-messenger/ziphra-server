package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;

import lombok.Data;

@Data
public class WrittingDTO {

	public String nickname;
	@PrivacityId
	public String idGrupo;
	
	@PrivacityId
	public String idUsuario;	
}
