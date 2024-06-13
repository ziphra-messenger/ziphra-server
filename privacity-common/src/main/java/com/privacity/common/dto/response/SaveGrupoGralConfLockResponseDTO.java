package com.privacity.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.dto.GrupoGralConfPasswordDTO;
import com.privacity.common.dto.LockDTO;

import lombok.Data;

@Data
public class SaveGrupoGralConfLockResponseDTO {

	@PrivacityId
	@JsonInclude(Include.NON_NULL)
	public String idGrupo;
	
	public GrupoGralConfPasswordDTO password;
	public LockDTO lock;


}
