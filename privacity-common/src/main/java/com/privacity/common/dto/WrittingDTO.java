package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.Data;

@Data
public class WrittingDTO {

	public String nickname;
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	public String idUsuario;	
}
