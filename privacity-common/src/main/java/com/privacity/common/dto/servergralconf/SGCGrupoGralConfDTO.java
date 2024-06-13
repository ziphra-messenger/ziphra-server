package com.privacity.common.dto.servergralconf;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.enumeration.ConfigurationStateEnum;

import lombok.Data;

@Data
public class SGCGrupoGralConfDTO {

	@PrivacityId
	public String idGrupo;
	
	public ConfigurationStateEnum resend;
	public ConfigurationStateEnum anonimo;
	
	public boolean timeMessageMandatory;
	public int timeMessageMaxTimeAllow;
	public ConfigurationStateEnum audiochat;

	public int audiochatMaxTime;
	public boolean blackMessageAttachMandatory;

	public boolean downloadAllowImage;
	public boolean downloadAllowAudio;
	public boolean downloadAllowVideo;
	
	public boolean changeNicknameToNumber;
	public boolean hideMessageDetails;
	public boolean hideMessageState;
	public boolean hideMemberList;
	public ConfigurationStateEnum extraEncrypt;

}
