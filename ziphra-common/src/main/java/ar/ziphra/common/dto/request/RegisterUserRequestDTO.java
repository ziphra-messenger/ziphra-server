package ar.ziphra.common.dto.request;

import java.io.Serializable;

import ar.ziphra.common.annotations.ZiphraIdExclude;
import ar.ziphra.common.dto.EncryptKeysDTO;

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
	@ZiphraIdExclude	
	private String username;
	@ZiphraIdExclude	
	private String nickname;
	@ZiphraIdExclude	
	private String password;

	private EncryptKeysDTO encryptKeysDTO;
	private EncryptKeysDTO invitationCodeEncryptKeysDTO;
}
