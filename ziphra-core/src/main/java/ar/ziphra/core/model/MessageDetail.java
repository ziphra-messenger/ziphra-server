package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import ar.ziphra.common.enumeration.MessageState;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class MessageDetail implements Serializable{
	private boolean hideRead;
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