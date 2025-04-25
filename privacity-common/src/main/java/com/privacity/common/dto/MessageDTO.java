package com.privacity.common.dto;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
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
public class MessageDTO implements Cloneable , IdGrupoInterface{

	@Override
	public String toString() {
		return "MessageDTO [idMessage=" + idMessage + ", idGrupo=" + idGrupo + ", text=" + text + ", systemMessage="
				+ systemMessage + ", MessagesDetailDTO=" + Arrays.toString(messagesDetail) + "]";
	}
	
	
	@PrivacityIdOrder
	private String idMessage;
	@PrivacityId
	@PrivacityIdOrder
	private String idGrupo;
	@PrivacityIdOrder
	private IdMessageDTO parentReply;
	@PrivacityIdOrder
    private IdMessageDTO parentResend;
    
    private UsuarioDTO usuarioCreacion;
	
    @PrivacityIdExclude
	private String text;
	
	
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @PrivacityIdExclude
	private boolean blackMessage;
    @PrivacityIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int timeMessage;
    @PrivacityIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean anonimo;
    @PrivacityIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean systemMessage;
    @PrivacityIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean secretKeyPersonal;
    @PrivacityIdExclude
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean blockResend;
    

    
	private MessageDetailDTO[] messagesDetail;
	private MediaDTO media;
	@PrivacityIdExclude
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private boolean changeNicknameToRandom;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@PrivacityIdExclude
	private boolean hideMessageDetails;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@PrivacityIdExclude
	
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