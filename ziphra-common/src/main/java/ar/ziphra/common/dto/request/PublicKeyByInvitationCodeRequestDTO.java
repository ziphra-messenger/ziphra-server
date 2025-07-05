package ar.ziphra.common.dto.request;

import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicKeyByInvitationCodeRequestDTO implements IdGrupoInterface{

	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;
	@ZiphraIdExclude	
	private String invitationCode;
	
}
