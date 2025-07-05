package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.enumeration.MessageState;
import ar.ziphra.common.interfaces.IdGrupoInterface;

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

	@ZiphraIdOrder	
	private String idMessage;
	@ZiphraId
	@ZiphraIdOrder	
	private String idGrupo;
	private UsuarioDTO usuarioDestino;
	@ZiphraIdExclude	
	private MessageState estado;
	@ZiphraIdExclude
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