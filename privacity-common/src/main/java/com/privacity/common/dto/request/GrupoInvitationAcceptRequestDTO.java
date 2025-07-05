package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoInvitationAcceptRequestDTO implements IdGrupoInterface{
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	private String idUsuarioInvitante;
	
	@PrivacityId
	@PrivacityIdOrder
	private String idUsuarioInvitado;
	
	private AESDTO aesDTO;
}
