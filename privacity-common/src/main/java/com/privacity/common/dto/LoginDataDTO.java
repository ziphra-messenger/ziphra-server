package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
public class LoginDataDTO {
	public String token;
	
	@PrivacityIdOrder
	@PrivacityId
	public String id;
	
	@JsonInclude(Include.NON_NULL)
	public String nickname;
	public String invitationCode;
	public String publicKey;
	
	public AESDTO sessionAESDTOWS;
	public AESDTO sessionAESDTOServerEncrypt;
	public MyAccountConfDTO myAccountGralConfDTO;
	
}
