package ar.ziphra.common.dto;

import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdMessageDTO implements IdGrupoInterface{

	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;
	
	@ZiphraIdOrder
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
