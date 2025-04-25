package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.CUSTOM)
public class UsuarioDTO{
	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idUsuario;
	@PrivacityIdExclude	
	@JsonInclude(Include.NON_NULL)
	private String nickname;
	@PrivacityIdExclude	
	@JsonInclude(Include.NON_NULL)
	private String alias;
	
//	public UserInvitationCodeDTO UserInvitationCodeDTO;
	@JsonInclude(Include.NON_NULL)
	private EncryptKeysDTO encryptKeysDTO;


}
