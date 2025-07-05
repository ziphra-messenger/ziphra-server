package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyByInvitationCodeRequestDTO implements IdGrupoInterface{

	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	@PrivacityIdExclude	
	private String invitationCode;
	
}
