package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.Data;

@Data
public class GrupoBlockRemotoRequestDTO implements IdGrupoInterface{

	@PrivacityId
	public String idGrupo;
	@PrivacityId
	public String idUsuario;





}
