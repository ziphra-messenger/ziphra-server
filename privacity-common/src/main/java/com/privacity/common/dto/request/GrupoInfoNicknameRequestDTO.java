package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;

import lombok.Data;

@Data
public class GrupoInfoNicknameRequestDTO {
	
	@PrivacityId
	public String idGrupo;
	public String nickname;

}
