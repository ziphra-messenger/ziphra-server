package com.privacity.server.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.privacity.common.enumeration.MessageState;
import com.privacity.server.security.Usuario;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("1")
public class MessageDetailDeleted extends MessageDetailBase implements Serializable{

    private boolean deleted;

	public MessageDetailId getMessageDetailId() {
		return messageDetailId;
	}
	public void setMessageDetailId(MessageDetailId messageDetailId) {
		this.messageDetailId = messageDetailId;
	}
	

	public MessageState getState() {
		return state;
	}
	public void setState(MessageState state) {
		this.state = state;
	}
	

    public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 888492831940808873L;

	@EmbeddedId
	private MessageDetailId messageDetailId; 
	


	private MessageState state;
    
  

}