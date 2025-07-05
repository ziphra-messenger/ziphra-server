package ar.ziphra.common.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
public class GrupoChangeUserRoleDTO implements IdGrupoInterface{
	
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;

	@ZiphraIdExclude	
	private GrupoRolesEnum role;
	
	@ZiphraId
	@ZiphraIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idUsuarioToChange;

}