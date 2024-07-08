package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.MessageState;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.Data;


@Data
public class MessageDetailDTO implements IdGrupoInterface{

	@PrivacityIdOrder	
	public String idMessage;
	@PrivacityId
	@PrivacityIdOrder	
	public String idGrupo;
	public UsuarioDTO usuarioDestino;
    public MessageState estado;
    public boolean deleted;
    
    public String buildIdMessageDetailToMap() {
    	String usuarioid=null;
    	if (usuarioDestino != null) {
    		usuarioid= usuarioDestino.getIdUsuario();
    	}
    	return idGrupo + "{-}" + idMessage + "{-}" + usuarioid;
    }
	    
    public String buildIdMessageToMap() {
    	return idGrupo + "{-}" + idMessage;
    }
}