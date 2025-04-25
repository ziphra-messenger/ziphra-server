package com.privacity.common.dto.request;

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
public class GrupoAddUserRequestDTO implements IdGrupoInterface{
	
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	@PrivacityIdExclude	
	public String invitationCode;
	
	@PrivacityIdExclude	
	public String invitationMessage;
	
	@PrivacityIdExclude	
	private GrupoRolesEnum role;
	private AESDTO aesDTO;
}