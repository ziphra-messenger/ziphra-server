package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.CUSTOM)

public class IdGrupoDTO implements IdGrupoInterface{
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	
}
