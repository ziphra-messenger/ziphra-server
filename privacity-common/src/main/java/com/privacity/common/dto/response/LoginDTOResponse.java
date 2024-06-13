package com.privacity.common.dto.response;

import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.LoginDataDTO;

import lombok.Data;
@Data
public class LoginDTOResponse {
	
	public AESDTO sessionAESDTO;
	public String privateKey;
	
	public LoginDataDTO loginDataDTO;

}
