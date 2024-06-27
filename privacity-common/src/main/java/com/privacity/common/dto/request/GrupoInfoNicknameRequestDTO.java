package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.Data;

@Data
public class GrupoInfoNicknameRequestDTO {
	
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	public String nickname;

}
