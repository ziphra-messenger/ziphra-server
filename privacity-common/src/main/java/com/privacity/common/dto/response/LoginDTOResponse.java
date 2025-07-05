package com.privacity.common.dto.response;

import java.io.Serializable;

import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.LoginDataDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTOResponse implements Serializable {

	private static final long serialVersionUID = -2374084006427173088L;
	
	private AESDTO sessionAESDTO;
	@PrivacityIdExclude	
	private String privateKey;
	
	private LoginDataDTO loginDataDTO;

}
