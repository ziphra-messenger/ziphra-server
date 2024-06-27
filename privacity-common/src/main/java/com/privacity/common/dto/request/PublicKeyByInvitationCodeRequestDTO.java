package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicKeyByInvitationCodeRequestDTO {

	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	public String invitationCode;
	
}
