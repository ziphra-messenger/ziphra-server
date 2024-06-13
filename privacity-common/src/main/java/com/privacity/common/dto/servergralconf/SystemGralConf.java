package com.privacity.common.dto.servergralconf;

import com.privacity.common.dto.LockDTO;

import lombok.Data;

@Data
public class SystemGralConf {

	private SGCAsymEncrypt AsymEncrypt;
	private SGCAuth auth;
	private MinMaxLenghtDTO requestId;
	
	private SGCAESDTO invitationAES;
	private SGCAESDTO messagingAES;

	private SGCAESDTO sessionAES;
	private SGCAESDTO publicAES;
	
	private boolean privacityIdAESOn;
	private SGCAESDTO privacityIdAES;
	
	private SGCAESSimple personalAES = new SGCAESSimple();
	
	// encriptacion personalidad extra por grupo
	private SGCAESSimple extraAES = new SGCAESSimple();

	
	private SGCMyAccountConfDTO myAccountConf;
	
	private SGCServerInfo serverInfo;
	
	// configuraciones varias
	private SGCExtrasDTO extras;
	
	private boolean messagingWritingMessage;
}
