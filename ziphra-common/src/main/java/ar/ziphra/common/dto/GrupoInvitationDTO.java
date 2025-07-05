package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.enumeration.GrupoRolesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class GrupoInvitationDTO {
	
	private UsuarioDTO usuarioInvitante;
	@ZiphraIdExclude	
	private GrupoRolesEnum role;
	private String invitationMessage;
	private AESDTO aesDTO;
	@ZiphraIdExclude	
	private String privateKey;
}
