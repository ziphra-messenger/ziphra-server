package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdMessageDTO implements IdGrupoInterface{

	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	
	@PrivacityIdOrder
	private String idMessage;
	
    public String buildIdMessageToMap() {
    	return idGrupo + "{-}" + idMessage;
    }	

    public Long convertIdGrupoToLong() {
		return Long.parseLong(idGrupo);
	}
	
	public Long convertIdMessageToLong() {
		return Long.parseLong(idMessage);
	}    
	
}
