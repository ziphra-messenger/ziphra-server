package com.privacity.common.dto.request;

import java.io.Serializable;

import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.dto.EncryptKeysDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5474314920738614640L;
	@PrivacityIdExclude	
	private String username;
	@PrivacityIdExclude	
	private String nickname;
	@PrivacityIdExclude	
	private String password;

	private EncryptKeysDTO encryptKeysDTO;
	private EncryptKeysDTO invitationCodeEncryptKeysDTO;
}
