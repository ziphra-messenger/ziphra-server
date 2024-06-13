package com.privacity.server.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorFormula;

import com.privacity.server.security.Usuario;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@Table(name = "message")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(
        "CASE WHEN deleted = true THEN '1' " +
        " WHEN deleted = false then '0'   end"
)
public class MessageBase implements Serializable {
	
	private static final long serialVersionUID = 2473293848377594179L;

	@EmbeddedId
	protected MessageId messageId; 
	
//    @ManyToOne(fetch = FetchType.LAZY)
//    @Getter
//    @Setter
//    private MessageBase parentResend;

//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentResend")
//    @Setter
//    @Getter
//    private Set<MessageBase> childrenResend;
    

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Getter
//    @Setter
//    private MessageBase parentReply;

//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parentReply")
//    @Setter
//    @Getter
//    private Set<MessageBase> childrenReply;	
    
	@ManyToOne
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
    
	private boolean permitirReenvio;
	
	private boolean changeNicknameToRandom;
	private boolean hideMessageDetails;
	private boolean hideMessageState;
	
	protected boolean deleted;

	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	public Set<MessageDetail> getMessagesDetail() {
		return messagesDetail;
	}

	
	public boolean isTimeMessage() {
		return timeMessage > 0;
	}




}