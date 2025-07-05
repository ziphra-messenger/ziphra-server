package com.privacity.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.dto.GrupoGralConfPasswordDTO;
import com.privacity.common.dto.LockDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SaveGrupoGralConfLockResponseDTO {

	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	private String idGrupo;
	
	private GrupoGralConfPasswordDTO password;
	private LockDTO lock;


}
