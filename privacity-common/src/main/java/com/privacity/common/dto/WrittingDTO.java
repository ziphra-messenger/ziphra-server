package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WrittingDTO implements IdGrupoInterface{
	private String nickname;
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	private String idUsuario;	
}
