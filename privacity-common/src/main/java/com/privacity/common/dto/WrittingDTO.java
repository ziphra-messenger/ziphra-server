package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.Data;

@Data
public class WrittingDTO implements IdGrupoInterface{
	public String nickname;
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	public String idUsuario;	
}
