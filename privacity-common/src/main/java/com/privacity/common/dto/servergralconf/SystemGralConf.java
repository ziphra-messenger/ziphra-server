package com.privacity.common.dto.servergralconf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	private SGCAESSimple personalAES;
	
	// encriptacion personalidad extra por grupo
	private SGCAESSimple extraAES = new SGCAESSimple();

	
	private SGCMyAccountConfDTO myAccountConf;
	
	private SGCServerInfo serverInfo;
	
	// configuraciones varias
	private SGCExtrasDTO extras;
	
	private boolean messagingWritingMessage;
	
	private PasswordConfigDTO passwordConfig;
}
