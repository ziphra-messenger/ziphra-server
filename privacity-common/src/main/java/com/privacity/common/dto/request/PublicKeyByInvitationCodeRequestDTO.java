package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicKeyByInvitationCodeRequestDTO implements IdGrupoInterface{

	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	public String invitationCode;
	
}
