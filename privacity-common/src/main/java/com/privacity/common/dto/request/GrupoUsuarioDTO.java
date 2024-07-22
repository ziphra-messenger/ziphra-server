package com.privacity.common.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;
import com.privacity.common.interfaces.IdUsuarioInterface;

import lombok.Data;

@Data
public class GrupoUsuarioDTO implements IdGrupoInterface, IdUsuarioInterface{
	
	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idUsuario;

}
