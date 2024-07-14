package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorFormula;

import com.privacity.common.enumeration.MessageState;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "message_detail")
@DiscriminatorFormula(
        "CASE WHEN deleted = true THEN '1' " +
        " WHEN deleted = false then '0'   end"
)
public class MessageDetailBase implements Serializable{

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