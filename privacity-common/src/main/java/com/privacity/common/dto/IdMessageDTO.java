package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdMessageDTO{

	@PrivacityId	
	public String idGrupo;
	
	@PrivacityIdOrder
	public String idMessage;
	
    public String getIdMessageToMap() {
    	return idGrupo + "{-}" + idMessage;
    }	

    
	
	public Long getIdGrupoLong() {
		return Long.parseLong(idGrupo);
	}
	
	public Long getIdMessageLong() {
		return Long.parseLong(idMessage);
	}    
	
}
