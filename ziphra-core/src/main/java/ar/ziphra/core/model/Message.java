package ar.ziphra.core.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Data
@Entity
@Accessors(chain = true)
public class Message implements Serializable {
	



	private boolean blockResend;
	public static final Long  CONSTANT_ID_STARTS_AT=10000L;
	private static final long serialVersionUID = 2473293848377594179L;

		@EmbeddedId
	protected MessageId messageId; 
	
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Getter
    @Setter
    private Message parentResend;
 
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentResend", cascade = {CascadeType.ALL})
    @Setter
    @Getter
    private Set<Message> childrenResend;
    

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @Getter
    @Setter
    private Message parentReply;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentReply", cascade = {CascadeType.ALL})
    @Setter
    @Getter
    private Set<Message> childrenReply;	
    

	@ManyToOne(fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
	@JoinColumn(name = "idUser")
    private Usuario userCreation;


    @OneToMany(fetch=FetchType.LAZY, mappedBy="messageDetailId.message", cascade = CascadeType.ALL) //don't fetch immediately in order to use power of caching
    private Set<MessageDetail> messagesDetail;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="mediaId.message", cascade = CascadeType.ALL) //don't fetch immediately in order to use power of caching

    private Media media;
    
	private boolean blackMessage;
    private int timeMessage;
    
    private boolean anonimo;
    private boolean systemMessage;
    private boolean secretKeyPersonal;
    @Column(length=14000)
    private String text;
    

	
	private boolean changeNicknameToRandom;
	private boolean hideMessageDetails;
	private boolean hideMessageState;
	protected boolean deleted;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
	public Set<MessageDetail> getMessagesDetail() {
		return messagesDetail;
	}

	
	public boolean isTimeMessage() {
		return timeMessage > 0;
	}






}