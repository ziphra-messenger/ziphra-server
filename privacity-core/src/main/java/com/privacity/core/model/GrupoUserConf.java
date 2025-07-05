package com.privacity.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.privacity.common.enumeration.RulesConfEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GrupoUserConf implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5751921389435803886L;

	@EmbeddedId
	private GrupoUserConfId grupoUserConfId; 

	private RulesConfEnum secretKeyPersonalAlways;
	private RulesConfEnum blackMessageAttachMandatory;
	private RulesConfEnum blackMessageAttachMandatoryReceived;
	private RulesConfEnum timeMessageAlways;
	private RulesConfEnum anonimoAlways;
	private RulesConfEnum blockResend
	;
	
	private Integer timeMessageSeconds;
	
	private boolean anonimoRecived;
	private boolean anonimoRecivedMyMessage;
	
	@Column(nullable = false)
	private RulesConfEnum blockMediaDownload;

	


}
