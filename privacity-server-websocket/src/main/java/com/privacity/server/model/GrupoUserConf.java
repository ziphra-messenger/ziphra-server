package com.privacity.server.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.privacity.common.enumeration.GrupoUserConfEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoUserConf {

	@EmbeddedId
	private GrupoUserConfId grupoUserConfId; 

	private GrupoUserConfEnum secretKeyPersonalAlways;
	private GrupoUserConfEnum blackMessageAlways;
	private GrupoUserConfEnum timeMessageAlways;
	private GrupoUserConfEnum anonimoAlways;
	private GrupoUserConfEnum permitirReenvio;
	
	private Integer timeMessageSeconds;
	
	private GrupoUserConfEnum blackMessageRecived;
	private GrupoUserConfEnum anonimoRecived;
	
	@Column(nullable = false)
	private GrupoUserConfEnum downloadAllowImage;
	@Column(nullable = false)
	private GrupoUserConfEnum downloadAllowAudio;
	@Column(nullable = false)
	private GrupoUserConfEnum downloadAllowVideo;
	


}
