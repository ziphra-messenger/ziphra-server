package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.MessageState;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;



@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.CUSTOM)
public class MessageDetailDTO implements IdGrupoInterface{

	@PrivacityIdOrder	
	private String idMessage;
	@PrivacityId
	@PrivacityIdOrder	
	private String idGrupo;
	private UsuarioDTO usuarioDestino;
	@PrivacityIdExclude	
	private MessageState estado;
	@PrivacityIdExclude
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean deleted;
	private boolean hideRead;
	private String error;

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