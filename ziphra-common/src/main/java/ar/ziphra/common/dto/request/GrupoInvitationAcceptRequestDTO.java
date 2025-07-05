package ar.ziphra.common.dto.request;

import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoInvitationAcceptRequestDTO implements IdGrupoInterface{
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;
	
	@ZiphraId
	@ZiphraIdOrder
	private String idUsuarioInvitante;
	
	@ZiphraId
	@ZiphraIdOrder
	private String idUsuarioInvitado;
	
	private AESDTO aesDTO;
}
