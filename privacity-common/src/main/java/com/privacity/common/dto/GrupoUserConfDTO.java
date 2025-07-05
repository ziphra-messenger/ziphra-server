
package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.RulesConfEnum;
import com.privacity.common.interfaces.IdGrupoInterface;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GrupoUserConfDTO implements IdGrupoInterface{

	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idUsuario; 


	@PrivacityIdExclude	
	public RulesConfEnum extraAesAlways;
	@PrivacityIdExclude	
	public RulesConfEnum blackMessageAttachMandatory;
	@PrivacityIdExclude	
	public RulesConfEnum timeMessageAlways;
	@PrivacityIdExclude	
	public RulesConfEnum anonimoAlways;
	@PrivacityIdExclude	
	public RulesConfEnum blockResend;
	@PrivacityIdExclude	
	public Integer timeMessageSeconds;
	@PrivacityIdExclude	
	public RulesConfEnum blackMessageAttachMandatoryReceived;
	@PrivacityIdExclude	
	public boolean anonimoRecived;
	@PrivacityIdExclude	
	private boolean anonimoRecivedMyMessage;
	@PrivacityIdExclude	
	public RulesConfEnum blockMediaDownload;


	

}