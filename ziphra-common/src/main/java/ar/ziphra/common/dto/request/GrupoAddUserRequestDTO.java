package ar.ziphra.common.dto.request;

import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoAddUserRequestDTO implements IdGrupoInterface{
	
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;
	@ZiphraIdExclude	
	public String invitationCode;
	
	@ZiphraIdExclude	
	public String invitationMessage;
	
	@ZiphraIdExclude	
	private GrupoRolesEnum role;
	private AESDTO aesDTO;
}