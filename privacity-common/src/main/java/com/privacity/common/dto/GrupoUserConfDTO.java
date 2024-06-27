
package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.GrupoUserConfEnum;

import lombok.Data;

@Data
public class GrupoUserConfDTO {

	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idGrupo;
	
	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idUsuario; 



	public GrupoUserConfEnum extraAesAlways;
	public GrupoUserConfEnum blackMessageAlways;
	public GrupoUserConfEnum timeMessageAlways;
	public GrupoUserConfEnum anonimoAlways;
	public GrupoUserConfEnum permitirReenvio;
	
	public Integer timeMessageSeconds;
	
	public GrupoUserConfEnum blackMessageRecived;
	public GrupoUserConfEnum anonimoRecived;
	
	
	public GrupoUserConfEnum downloadAllowImage;
	public GrupoUserConfEnum downloadAllowAudio;
	public GrupoUserConfEnum downloadAllowVideo;
	
	public String alias;

	

}