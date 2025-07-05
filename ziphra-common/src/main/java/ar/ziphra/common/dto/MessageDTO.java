package ar.ziphra.common.dto;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.annotations.ZiphraIdOrder;
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
public class MessageDTO implements Cloneable , IdGrupoInterface{

	@Override
	public String toString() {
		return "MessageDTO [idMessage=" + idMessage + ", idGrupo=" + idGrupo + ", text=" + text + ", systemMessage="
				+ systemMessage + ", MessagesDetailDTO=" + Arrays.toString(messagesDetail) + "]";
	}
	
	
	@ZiphraIdOrder
	private String idMessage;
	@ZiphraId
	@ZiphraIdOrder
	private String idGrupo;
	@ZiphraIdOrder
	private IdMessageDTO parentReply;
	@ZiphraIdOrder
    private IdMessageDTO parentResend;
    
    private UsuarioDTO usuarioCreacion;
	
    @ZiphraIdExclude
	private String text;
	
	
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @ZiphraIdExclude
	private boolean blackMessage;
    @ZiphraIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int timeMessage;
    @ZiphraIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean anonimo;
    @ZiphraIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean systemMessage;
    @ZiphraIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean secretKeyPersonal;
    @ZiphraIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean blockResend;
    

    
	private MessageDetailDTO[] messagesDetail;
	private MediaDTO media;
	@ZiphraIdExclude
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean changeNicknameToRandom;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@ZiphraIdExclude
	private boolean hideMessageDetails;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@ZiphraIdExclude
	
	private boolean hideMessageReadState;

	
	@Override
	public MessageDTO clone() throws CloneNotSupportedException {
		return (MessageDTO)super.clone();
	}

    public String buildIdMessageToMap() {
    	return idGrupo + "{-}" + idMessage;
    }
    
    public IdMessageDTO buildIdMessageDTO(){
        return new IdMessageDTO(this.getIdGrupo(),this.getIdMessage());    
    }



}