package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.privacity.common.enumeration.ConfigurationStateEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoGralConf implements Serializable{
	
	public GrupoGralConf(Grupo grupo) {
		this.grupo=grupo;
	}
	
	private static final long serialVersionUID = -3324613031997024293L;

	@Id
	@OneToOne
	@ToString.Exclude	
	private Grupo grupo;
	
	private boolean resend;
	private ConfigurationStateEnum anonimo;
	
	private boolean timeMessageMandatory;
	private int timeMessageMaxTimeAllow;
	private ConfigurationStateEnum audiochat;

	private int audiochatMaxTime;
	private boolean blackMessageAttachMandatory;

	private boolean downloadAllowImage;
	private boolean downloadAllowAudio;
	private boolean downloadAllowVideo;
	
	private boolean changeNicknameToNumber;
	private boolean hideMessageDetails;
	private boolean hideMessageState;
	private boolean hideMemberList;
	private ConfigurationStateEnum extraEncrypt;
	
  
	
	
}
