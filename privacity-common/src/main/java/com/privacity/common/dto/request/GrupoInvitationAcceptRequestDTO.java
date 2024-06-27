package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.dto.AESDTO;

import lombok.Data;

@Data
public class GrupoInvitationAcceptRequestDTO {
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	public String idUsuarioInvitante;
	
	@PrivacityId
	@PrivacityIdOrder
	public String idUsuarioInvitado;
	
	public AESDTO aesDTO;
}
