package ar.ziphra.common.dto.servergralconf;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.Data;
import lombok.experimental.Accessors;
@Accessors(chain = true)
@Data
@ZiphraIdExclude
public class PasswordConfigDTO {

	private PasswordRulesDTO usuario;
	private PasswordRulesDTO passwordUsuarioRegistration;
	private PasswordRulesDTO passwordGrupo;
	private PasswordRulesDTO passwordExtraEncrypt;
	private PasswordRulesDTO nickname;
}
