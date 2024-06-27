package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.enumeration.GrupoRolesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoAddUserRequestDTO{
	
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	public String invitationCode;
	public GrupoRolesEnum role;
	public AESDTO aesDTO;
}