package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.enumeration.GrupoRolesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class GrupoInvitationDTO {
	
	private UsuarioDTO usuarioInvitante;
	@PrivacityIdExclude	
	private GrupoRolesEnum role;
	private String invitationMessage;
	private AESDTO aesDTO;
	@PrivacityIdExclude	
	private String privateKey;
}
