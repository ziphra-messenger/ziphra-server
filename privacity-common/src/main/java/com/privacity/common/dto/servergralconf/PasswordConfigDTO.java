package com.privacity.common.dto.servergralconf;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.Data;
import lombok.experimental.Accessors;
@Accessors(chain = true)
@Data
@PrivacityIdExclude
public class PasswordConfigDTO {

	private PasswordRulesDTO usuario;
	private PasswordRulesDTO passwordUsuarioRegistration;
	private PasswordRulesDTO passwordGrupo;
	private PasswordRulesDTO passwordExtraEncrypt;
	private PasswordRulesDTO nickname;
}
