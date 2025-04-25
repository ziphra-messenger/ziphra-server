package com.privacity.common.dto.servergralconf;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@PrivacityIdExclude
@Accessors(chain = true)
public class PasswordRulesDTO {
		
	
	private int lenghtMandatoryMin;
	private int lenghtMandatoryMax;
	private boolean lenghtSugerenciaMinEnabled;
	private int lenghtSugerenciaMin;
	private boolean uppercaseMandatoryEnabled;
	private int uppercaseMandatoryQuantity;
	private boolean uppercaseSugerenciaEnabled;
	private int uppercaseSugerenciaQuantity;
	private boolean lowercaseMandatoryEnabled;
	private int lowercaseMandatoryQuantity;
	private boolean lowercaseSugerenciaEnabled;
	private int lowercaseSugerenciaQuantity;
	private boolean especialcharMandatoryEnabled;
	private int especialcharMandatoryQuantity;
	private boolean especialcharSugerenciaEnabled;
	private int especialcharSugerenciaQuantity;
	private boolean digitNumberMandatoryEnabled;
	private int digitNumberMandatoryQuantity;
	private boolean digitNumberSugerenciaEnabled;
	private int digitNumberSugerenciaQuantity;
	private boolean repeatCharRestrictEnabled;
	private int repeatCharRestrictQuantity;
	private boolean repeatCharSugerenciaEnabled;
	private int repeatCharSugerenciaQuantity;
	private boolean whitespaceRestrictEnabled;
	private boolean whitespaceSugerenciaEnabled;
	private boolean usernameRestrictEnabled;
	private boolean usernameSugerenciaEnabled;
	private boolean nicknameRestrictEnabled;
	private boolean nicknameSugerenciaEnabled;
	private boolean passwordRestrictEnabled;
	private boolean passwordSugerenciaEnabled;

}
