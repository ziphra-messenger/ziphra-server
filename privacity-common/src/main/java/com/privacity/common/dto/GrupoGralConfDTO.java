package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.ConfigurationStateEnum;

import lombok.Data;

@Data
public class GrupoGralConfDTO {

	@PrivacityId
	@PrivacityIdOrder
	@JsonInclude(Include.NON_NULL)
	public String idGrupo;
	
	@JsonInclude(Include.NON_NULL)
	public boolean resend;
	
	@JsonInclude(Include.NON_NULL)
	public ConfigurationStateEnum anonimo;
	
	@JsonInclude(Include.NON_NULL)
	public boolean timeMessageMandatory;
	
	@JsonInclude(Include.NON_NULL)
	public int timeMessageMaxTimeAllow;
	
	@JsonInclude(Include.NON_NULL)
	public ConfigurationStateEnum audiochat;
	
	@JsonInclude(Include.NON_NULL)
	public int audiochatMaxTime;
	
	@JsonInclude(Include.NON_NULL)
	public boolean blackMessageAttachMandatory;
	
	@JsonInclude(Include.NON_NULL)
	public ConfigurationStateEnum downloadAllowImage;
	
	@JsonInclude(Include.NON_NULL)
	public ConfigurationStateEnum downloadAllowAudio;
	
	@JsonInclude(Include.NON_NULL)
	public ConfigurationStateEnum downloadAllowVideo;
	
	@JsonInclude(Include.NON_NULL)
	public boolean changeNicknameToNumber;
	
	@JsonInclude(Include.NON_NULL)
	public boolean hideMessageDetails;
	
	@JsonInclude(Include.NON_NULL)
	public boolean hideMessageState;
	
	@JsonInclude(Include.NON_NULL)
	public boolean hideMemberList;
	
	@JsonInclude(Include.NON_NULL)
	public ConfigurationStateEnum extraEncrypt;


}
