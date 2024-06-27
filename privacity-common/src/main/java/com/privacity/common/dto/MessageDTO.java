package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO implements Cloneable{
	@PrivacityIdOrder
	public String idMessage;
	@PrivacityId
	@PrivacityIdOrder
	public String idGrupo;
	
    public IdMessageDTO parentReply;
    public IdMessageDTO parentResend;
    
	public UsuarioDTO usuarioCreacion;
	

	public String text;
	
	
    public boolean blackMessage;
    public int timeMessage;
    public boolean anonimo;
    public boolean systemMessage;
    public boolean secretKeyPersonal;
    
    public boolean permitirReenvio;
    

    
	public MessageDetailDTO[] MessagesDetailDTO;
	public MediaDTO MediaDTO;
	
	public boolean changeNicknameToRandom;
	public boolean hideMessageDetails;
	public boolean hideMessageState;
	
	@Override
	public MessageDTO clone() throws CloneNotSupportedException {
		return (MessageDTO)super.clone();
	}

    public String getIdMessageToMap() {
    	return idGrupo + "{-}" + idMessage;
    }
    
    public boolean isTimeMessage() {
    	return timeMessage > 0;
    }
    
    public IdMessageDTO getIdMessageDTO(){
        return new IdMessageDTO(this.getIdGrupo(),this.getIdMessage());    
    }

	public String getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public IdMessageDTO getParentReply() {
		return parentReply;
	}

	public void setParentReply(IdMessageDTO parentReply) {
		this.parentReply = parentReply;
	}

	public IdMessageDTO getParentResend() {
		return parentResend;
	}

	public void setParentResend(IdMessageDTO parentResend) {
		this.parentResend = parentResend;
	}

	public UsuarioDTO getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(UsuarioDTO usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isBlackMessage() {
		return blackMessage;
	}

	public void setBlackMessage(boolean blackMessage) {
		this.blackMessage = blackMessage;
	}

	public int getTimeMessage() {
		return timeMessage;
	}

	public void setTimeMessage(int timeMessage) {
		this.timeMessage = timeMessage;
	}

	public boolean isAnonimo() {
		return anonimo;
	}

	public void setAnonimo(boolean anonimo) {
		this.anonimo = anonimo;
	}

	public boolean isSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(boolean systemMessage) {
		this.systemMessage = systemMessage;
	}

	public boolean isSecretKeyPersonal() {
		return secretKeyPersonal;
	}

	public void setSecretKeyPersonal(boolean secretKeyPersonal) {
		this.secretKeyPersonal = secretKeyPersonal;
	}

	public boolean isPermitirReenvio() {
		return permitirReenvio;
	}

	public void setPermitirReenvio(boolean permitirReenvio) {
		this.permitirReenvio = permitirReenvio;
	}

	public MessageDetailDTO[] getMessagesDetailDTO() {
		return MessagesDetailDTO;
	}

	public void setMessagesDetailDTO(MessageDetailDTO[] messagesDetailDTO) {
		MessagesDetailDTO = messagesDetailDTO;
	}

	public MediaDTO getMediaDTO() {
		return MediaDTO;
	}

	public void setMediaDTO(MediaDTO mediaDTO) {
		MediaDTO = mediaDTO;
	}

	public boolean isChangeNicknameToRandom() {
		return changeNicknameToRandom;
	}

	public void setChangeNicknameToRandom(boolean changeNicknameToRandom) {
		this.changeNicknameToRandom = changeNicknameToRandom;
	}

	public boolean isHideMessageDetails() {
		return hideMessageDetails;
	}

	public void setHideMessageDetails(boolean hideMessageDetails) {
		this.hideMessageDetails = hideMessageDetails;
	}

	public boolean isHideMessageState() {
		return hideMessageState;
	}

	public void setHideMessageState(boolean hideMessageState) {
		this.hideMessageState = hideMessageState;
	}     


}