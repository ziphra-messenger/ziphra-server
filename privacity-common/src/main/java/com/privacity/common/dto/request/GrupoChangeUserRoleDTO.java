package com.privacity.common.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoChangeUserRoleDTO implements IdGrupoInterface{
	
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;

	@PrivacityIdExclude	
	private GrupoRolesEnum role;
	
	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idUsuarioToChange;

}