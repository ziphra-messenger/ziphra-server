package ar.ziphra.common.dto.request;

import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.interfaces.IdGrupoInterface;
import ar.ziphra.common.interfaces.IdUsuarioInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoRemoveUserDTO implements IdGrupoInterface, IdUsuarioInterface{
	
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;

	@ZiphraId
	@ZiphraIdOrder
	private String idUsuario;
	

}