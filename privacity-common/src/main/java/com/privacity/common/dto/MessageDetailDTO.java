package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.Data;


@Data
public class MessageDetailDTO{

	@PrivacityIdOrder	
	public String idMessage;
	@PrivacityId
	@PrivacityIdOrder	
	public String idGrupo;
	public UsuarioDTO usuarioDestino;
    public String estado;
    private boolean deleted;
    
    public String getIdMessageDetailToMap() {
    	String usuarioid=null;
    	if (usuarioDestino != null) {
    		usuarioid= usuarioDestino.getIdUsuario();
    	}
    	return idGrupo + "{-}" + idMessage + "{-}" + usuarioid;
    }
	    
    public String getIdMessageToMap() {
    	return idGrupo + "{-}" + idMessage;
    }
}