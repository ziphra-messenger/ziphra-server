package com.privacity.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.privacity.common.annotations.PrivacityId;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class LoginDataDTO {
	public String token;
	
	@PrivacityId
	public String id;
	@JsonInclude(Include.NON_NULL)
	public String nickname;
	public String invitationCode;
	public String publicKey;
	
	public MyAccountConfDTO myAccountGralConfDTO;
	
}
