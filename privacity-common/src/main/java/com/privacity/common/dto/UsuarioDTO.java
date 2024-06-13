package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;

import lombok.Data;

@Data
public class UsuarioDTO{
	@PrivacityId
	@JsonInclude(Include.NON_NULL)
	public String idUsuario;
	@JsonInclude(Include.NON_NULL)
	public String nickname;
	@JsonInclude(Include.NON_NULL)
	public String alias;
//	public UserInvitationCodeDTO UserInvitationCodeDTO;
	@JsonInclude(Include.NON_NULL)
	public EncryptKeysDTO encryptKeysDTO;


}
