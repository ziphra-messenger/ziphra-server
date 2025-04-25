package com.privacity.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginDataDTO implements Serializable{

	private static final long serialVersionUID = -4081347203080964793L;
	@PrivacityIdExclude	
	private String token;
	
	@PrivacityIdOrder
	@PrivacityId
	private String id;
	
	@JsonInclude(Include.NON_NULL)
	@PrivacityIdExclude	
	private String nickname;
	@PrivacityIdExclude	
	private String invitationCode;
	@PrivacityIdExclude	
	private String publicKey;
	
	private AESDTO sessionAESDTOWS;
	private AESDTO sessionAESDTOServerEncrypt;
	private MyAccountConfDTO myAccountGralConfDTO;
	
}
