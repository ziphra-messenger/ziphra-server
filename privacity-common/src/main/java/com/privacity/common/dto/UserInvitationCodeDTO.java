package com.privacity.common.dto;

import lombok.Data;

@Data
public class UserInvitationCodeDTO {

	   public EncryptKeysDTO encryptKeysDTO;
	   public String invitationCode;
}
