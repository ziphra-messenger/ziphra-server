package com.privacity.server.component.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.LockDTO;
import com.privacity.common.dto.servergralconf.MinMaxLenghtDTO;
import com.privacity.common.dto.servergralconf.PasswordConfigDTO;
import com.privacity.common.dto.servergralconf.PasswordRulesDTO;
import com.privacity.common.dto.servergralconf.SGCAESDTO;
import com.privacity.common.dto.servergralconf.SGCAESSimple;
import com.privacity.common.dto.servergralconf.SGCAsymEncrypt;
import com.privacity.common.dto.servergralconf.SGCAuth;
import com.privacity.common.dto.servergralconf.SGCExtrasDTO;
import com.privacity.common.dto.servergralconf.SGCInvitationCode;
import com.privacity.common.dto.servergralconf.SGCMyAccountConfDTO;
import com.privacity.common.dto.servergralconf.SGCServerInfo;
import com.privacity.common.dto.servergralconf.SystemGralConf;
import com.privacity.common.enumeration.RandoGeneratorCase;
import com.privacity.common.enumeration.RandomGeneratorType;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ServerConfService {
	private SystemGralConf systemGralConf;
	//SGCAsymEncrypt
	@Value("${serverconf.asymEncrypt.type}")
	private String AsymEncrypt_type;
	@Value("${serverconf.asymEncrypt.bits}")
	private int AsymEncrypt_bits; 
	
	//SGCAuth
	@Value("${serverconf.auth.tokenType}")
	private String SGCAuth_tokenType;
	@Value("${serverconf.auth.tokenLenght}")
	private int SGCAuth_tokenLenght;
	
	//requestId
	@Value("${serverconf.requestId.minLenght}")
	private int requestId_minLenght;
	@Value("${serverconf.requestId.maxLenght}")
	private int requestId_maxLenght;	

	// personalAES
	@Value("${serverconf.personalAES.bits}")
	private int personalAES_bits;

	// extraAES
	@Value("${serverconf.extraAES.iterationMaxValue}")
	private int extraAES_iterationValue;
	@Value("${serverconf.extraAES.bits}")
	private int extraAES_bits;	

	
	// messagingAes
	@Value("${serverconf.messagingAes.keyMinLenght}")
	private int messagingAes_keyMinLenght;
	@Value("${serverconf.messagingAes.keyMaxLenght}")
	private int messagingAes_keyMaxLenght;
	@Value("${serverconf.messagingAes.saltMinLenght}")
	private int messagingAes_saltMinLenght;
	@Value("${serverconf.messagingAes.saltMaxLenght}")
	private int messagingAes_saltMaxLenght;	
	@Value("${serverconf.messagingAes.iterationMinValue}")
	private int messagingAes_iterationMinValue;
	@Value("${serverconf.messagingAes.iterationMaxValue}")
	private int messagingAes_iterationMaxValue;
	@Value("${serverconf.messagingAes.bits}")
	private int messagingAes_bits;
	@Value("${serverconf.messagingAes.randomGeneratorType}")	
	private RandomGeneratorType messagingAes_randomGeneratorType;
	
	//invitationAes
	@Value("${serverconf.invitationAes.keyMinLenght}")
	private int invitationAes_keyMinLenght;
	@Value("${serverconf.invitationAes.keyMaxLenght}")
	private int invitationAes_keyMaxLenght;
	@Value("${serverconf.invitationAes.saltMinLenght}")
	private int invitationAes_saltMinLenght;
	@Value("${serverconf.invitationAes.saltMaxLenght}")
	private int invitationAes_saltMaxLenght;	
	@Value("${serverconf.invitationAes.iterationMinValue}")
	private int invitationAes_iterationMinValue;
	@Value("${serverconf.invitationAes.iterationMaxValue}")
	private int invitationAes_iterationMaxValue;
	@Value("${serverconf.invitationAes.bits}")
	private int invitationAes_bits;
	@Value("${serverconf.invitationAes.randomGeneratorType}")	
	private RandomGeneratorType invitationAes_randomGeneratorType;
	
	// sessionAES
	@Value("${serverconf.sessionAes.keyMinLenght}")
	private int sessionAES_keyMinLenght;
	@Value("${serverconf.sessionAes.keyMaxLenght}")
	private int sessionAES_keyMaxLenght;
	@Value("${serverconf.sessionAes.saltMinLenght}")
	private int sessionAES_saltMinLenght;
	@Value("${serverconf.sessionAes.saltMaxLenght}")
	private int sessionAES_saltMaxLenght;	
	@Value("${serverconf.sessionAes.iterationMinValue}")
	private int sessionAES_iterationMinValue;
	@Value("${serverconf.sessionAes.iterationMaxValue}")
	private int sessionAES_iterationMaxValue;
	@Value("${serverconf.sessionAes.bits}")
	private int sessionAES_bits;
	@Value("${serverconf.sessionAes.randomGeneratorType}")	
	private RandomGeneratorType sessionAES_randomGeneratorType;

	// publicAES
	@Value("${serverconf.publicAes.keyMinLenght}")
	private int publicAES_keyMinLenght;
	@Value("${serverconf.publicAes.keyMaxLenght}")
	private int publicAES_keyMaxLenght;
	@Value("${serverconf.publicAes.saltMinLenght}")
	private int publicAES_saltMinLenght;
	@Value("${serverconf.publicAes.saltMaxLenght}")
	private int publicAES_saltMaxLenght;	
	@Value("${serverconf.publicAes.iterationMinValue}")
	private int publicAES_iterationMinValue;
	@Value("${serverconf.publicAes.iterationMaxValue}")
	private int publicAES_iterationMaxValue;
	@Value("${serverconf.publicAes.bits}")
	private int publicAES_bits;
	@Value("${serverconf.publicAes.randomGeneratorType}")
	private RandomGeneratorType publicAES_randomGeneratorType;

	// privacityIdAES
	@Value("${serverconf.privacityIdAes.keyMinLenght}")
	private int privacityIdAES_keyMinLenght;
	@Value("${serverconf.privacityIdAes.keyMaxLenght}")
	private int privacityIdAES_keyMaxLenght;
	@Value("${serverconf.privacityIdAes.saltMinLenght}")
	private int privacityIdAES_saltMinLenght;
	@Value("${serverconf.privacityIdAes.saltMaxLenght}")
	private int privacityIdAES_saltMaxLenght;	
	@Value("${serverconf.privacityIdAes.iterationMinValue}")
	private int privacityIdAES_iterationMinValue;
	@Value("${serverconf.privacityIdAes.iterationMaxValue}")
	private int privacityIdAES_iterationMaxValue;
	@Value("${serverconf.privacityIdAes.bits}")
	private int privacityIdAES_bits;
	@Value("${serverconf.privacityIdAes.randomGeneratorType}")
	private RandomGeneratorType privacityIdAES_randomGeneratorType;

	// privacityIdAESOn
	@Value("${serverconf.privacityIdAESOn}")	
	private boolean privacityIdAESOn;

	// serverconf.messaging.writing.message
	@Value("${serverconf.messaging.writing.message}")	
	private boolean messagingWritingMessage;
	
	// SGCMyAccountConfDTO
	@Value("${serverconf.myAccountConf.resend}")
	private boolean myAccountConf_resend;
	@Value("${serverconf.myAccountConf.blackMessageAttachMandatory}")
	private boolean myAccountConf_blackMessageAttachMandatory;
	@Value("${serverconf.myAccountConf.downloadAttachAllowImage}")
	private boolean myAccountConf_downloadAttachAllowImage;
	@Value("${serverconf.myAccountConf.hideMyMessageState}")
	private boolean myAccountConf_hideMyMessageState;
	@Value("${serverconf.myAccountConf.hideMyInDetails}")
	private boolean myAccountConf_hideMyInDetails;
	@Value("${serverconf.myAccountConf.timeMessageAlways}")
	private boolean myAccountConf_timeMessageAlways;
	@Value("${serverconf.myAccountConf.timeMessageDefaultTime}")
	private long myAccountConf_timeMessageDefaultTime;
	@Value("${serverconf.myAccountConf.audioMaxTimeInSeconds}")
	private int myAccountConf_audioMaxTimeInSeconds;
	@Value("${serverconf.myAccountConf.invitationCode.lenght.min}")
	private int myAccountConf_invitationCode_lenght_min;
	@Value("${serverconf.myAccountConf.invitationCode.lenght.max}")
	private int myAccountConf_invitationCode_lenght_max;
	@Value("${serverconf.myAccountConf.invitationCode.caseSet}")
	private RandoGeneratorCase myAccountConf_invitationCode_caseSet;

	//SGCServerInfo
	@Value("${serverconf.serverInfo.apiRestUrl}")
	private String serverInfo_apiRestUrl;
	@Value("${serverconf.serverInfo.wsUrl}")
	private String serverInfo_wsUrl;
	@Value("${serverconf.serverInfo.name}")
	private String serverInfo_name;
	@Value("${serverconf.serverInfo.version}")
	private String serverInfo_version;
	
	// privacityIdAESOn
	@Value("${serverconf.extras.screenshotEnabled}")	
	private boolean extrasScreenshotEnabled;
	
	// lock
	@Value("${serverconf.myAccountConf_lock.enabled}")	
	private boolean myAccountConf_lock_enabled;
	
	@Value("${serverconf.myAccountConf_lock.seconds}")	
	private int myAccountConf_lock_seconds;
	
	@Value("${serverconf.myAccountConf_lock.min.seconds.validation}")	
	private int myAccountConf_lock_min_seconds_validation;	

	@Value("${usuario.validation.rule.lenght.mandatory.min}")
	private int usuario_validationRuleLenghtMandatoryMin;
	@Value("${usuario.validation.rule.lenght.mandatory.max}")
	private int usuario_validationRuleLenghtMandatoryMax;
	@Value("${usuario.validation.rule.lenght.sugerencia.min.enabled}")
	private boolean usuario_validationRuleLenghtSugerenciaMinEnabled;
	@Value("${usuario.validation.rule.lenght.sugerencia.min}")
	private int usuario_validationRuleLenghtSugerenciaMin;
	@Value("${usuario.validation.rule.uppercase.mandatory.enabled}")
	private boolean usuario_validationRuleUppercaseMandatoryEnabled;
	@Value("${usuario.validation.rule.uppercase.mandatory.quantity}")
	private int usuario_validationRuleUppercaseMandatoryQuantity;
	@Value("${usuario.validation.rule.uppercase.sugerencia.enabled}")
	private boolean usuario_validationRuleUppercaseSugerenciaEnabled;
	@Value("${usuario.validation.rule.uppercase.sugerencia.quantity}")
	private int usuario_validationRuleUppercaseSugerenciaQuantity;
	@Value("${usuario.validation.rule.lowercase.mandatory.enabled}")
	private boolean usuario_validationRuleLowercaseMandatoryEnabled;
	@Value("${usuario.validation.rule.lowercase.mandatory.quantity}")
	private int usuario_validationRuleLowercaseMandatoryQuantity;
	@Value("${usuario.validation.rule.lowercase.sugerencia.enabled}")
	private boolean usuario_validationRuleLowercaseSugerenciaEnabled;
	@Value("${usuario.validation.rule.lowercase.sugerencia.quantity}")
	private int usuario_validationRuleLowercaseSugerenciaQuantity;
	@Value("${usuario.validation.rule.especialchar.mandatory.enabled}")
	private boolean usuario_validationRuleEspecialcharMandatoryEnabled;
	@Value("${usuario.validation.rule.especialchar.mandatory.quantity}")
	private int usuario_validationRuleEspecialcharMandatoryQuantity;
	@Value("${usuario.validation.rule.especialchar.sugerencia.enabled}")
	private boolean usuario_validationRuleEspecialcharSugerenciaEnabled;
	@Value("${usuario.validation.rule.especialchar.sugerencia.quantity}")
	private int usuario_validationRuleEspecialcharSugerenciaQuantity;
	@Value("${usuario.validation.rule.digit.number.mandatory.enabled}")
	private boolean usuario_validationRuleDigitNumberMandatoryEnabled;
	@Value("${usuario.validation.rule.digit.number.mandatory.quantity}")
	private int usuario_validationRuleDigitNumberMandatoryQuantity;
	@Value("${usuario.validation.rule.digit.number.sugerencia.enabled}")
	private boolean usuario_validationRuleDigitNumberSugerenciaEnabled;
	@Value("${usuario.validation.rule.digit.number.sugerencia.quantity}")
	private int usuario_validationRuleDigitNumberSugerenciaQuantity;
	@Value("${usuario.validation.rule.repeat.char.restrict.enabled}")
	private boolean usuario_validationRuleRepeatCharRestrictEnabled;
	@Value("${usuario.validation.rule.repeat.char.restrict.quantity}")
	private int usuario_validationRuleRepeatCharRestrictQuantity;
	@Value("${usuario.validation.rule.repeat.char.sugerencia.enabled}")
	private boolean usuario_validationRuleRepeatCharSugerenciaEnabled;
	@Value("${usuario.validation.rule.repeat.char.sugerencia.quantity}")
	private int usuario_validationRuleRepeatCharSugerenciaQuantity;
	@Value("${usuario.validation.rule.whitespace.restrict.enabled}")
	private boolean usuario_validationRuleWhitespaceRestrictEnabled;
	@Value("${usuario.validation.rule.whitespace.sugerencia.enabled}")
	private boolean usuario_validationRuleWhitespaceSugerenciaEnabled;
	@Value("${usuario.validation.rule.username.restrict.enabled}")
	private boolean usuario_validationRuleUsernameRestrictEnabled;
	@Value("${usuario.validation.rule.username.sugerencia.enabled}")
	private boolean usuario_validationRuleUsernameSugerenciaEnabled;
	@Value("${usuario.validation.rule.nickname.restrict.enabled}")
	private boolean usuario_validationRuleNicknameRestrictEnabled;
	@Value("${usuario.validation.rule.nickname.sugerencia.enabled}")
	private boolean usuario_validationRuleNicknameSugerenciaEnabled;
	@Value("${usuario.validation.rule.password.restrict.enabled}")
	private boolean usuario_validationRulePasswordRestrictEnabled;
	@Value("${usuario.validation.rule.password.sugerencia.enabled}")
	private boolean usuario_validationRulePasswordSugerenciaEnabled;
	
	@Value("${nickname.validation.rule.lenght.mandatory.min}")
	private int nickname_validationRuleLenghtMandatoryMin;
	@Value("${nickname.validation.rule.lenght.mandatory.max}")
	private int nickname_validationRuleLenghtMandatoryMax;
	@Value("${nickname.validation.rule.lenght.sugerencia.min.enabled}")
	private boolean nickname_validationRuleLenghtSugerenciaMinEnabled;
	@Value("${nickname.validation.rule.lenght.sugerencia.min}")
	private int nickname_validationRuleLenghtSugerenciaMin;
	@Value("${nickname.validation.rule.uppercase.mandatory.enabled}")
	private boolean nickname_validationRuleUppercaseMandatoryEnabled;
	@Value("${nickname.validation.rule.uppercase.mandatory.quantity}")
	private int nickname_validationRuleUppercaseMandatoryQuantity;
	@Value("${nickname.validation.rule.uppercase.sugerencia.enabled}")
	private boolean nickname_validationRuleUppercaseSugerenciaEnabled;
	@Value("${nickname.validation.rule.uppercase.sugerencia.quantity}")
	private int nickname_validationRuleUppercaseSugerenciaQuantity;
	@Value("${nickname.validation.rule.lowercase.mandatory.enabled}")
	private boolean nickname_validationRuleLowercaseMandatoryEnabled;
	@Value("${nickname.validation.rule.lowercase.mandatory.quantity}")
	private int nickname_validationRuleLowercaseMandatoryQuantity;
	@Value("${nickname.validation.rule.lowercase.sugerencia.enabled}")
	private boolean nickname_validationRuleLowercaseSugerenciaEnabled;
	@Value("${nickname.validation.rule.lowercase.sugerencia.quantity}")
	private int nickname_validationRuleLowercaseSugerenciaQuantity;
	@Value("${nickname.validation.rule.especialchar.mandatory.enabled}")
	private boolean nickname_validationRuleEspecialcharMandatoryEnabled;
	@Value("${nickname.validation.rule.especialchar.mandatory.quantity}")
	private int nickname_validationRuleEspecialcharMandatoryQuantity;
	@Value("${nickname.validation.rule.especialchar.sugerencia.enabled}")
	private boolean nickname_validationRuleEspecialcharSugerenciaEnabled;
	@Value("${nickname.validation.rule.especialchar.sugerencia.quantity}")
	private int nickname_validationRuleEspecialcharSugerenciaQuantity;
	@Value("${nickname.validation.rule.digit.number.mandatory.enabled}")
	private boolean nickname_validationRuleDigitNumberMandatoryEnabled;
	@Value("${nickname.validation.rule.digit.number.mandatory.quantity}")
	private int nickname_validationRuleDigitNumberMandatoryQuantity;
	@Value("${nickname.validation.rule.digit.number.sugerencia.enabled}")
	private boolean nickname_validationRuleDigitNumberSugerenciaEnabled;
	@Value("${nickname.validation.rule.digit.number.sugerencia.quantity}")
	private int nickname_validationRuleDigitNumberSugerenciaQuantity;
	@Value("${nickname.validation.rule.repeat.char.restrict.enabled}")
	private boolean nickname_validationRuleRepeatCharRestrictEnabled;
	@Value("${nickname.validation.rule.repeat.char.restrict.quantity}")
	private int nickname_validationRuleRepeatCharRestrictQuantity;
	@Value("${nickname.validation.rule.repeat.char.sugerencia.enabled}")
	private boolean nickname_validationRuleRepeatCharSugerenciaEnabled;
	@Value("${nickname.validation.rule.repeat.char.sugerencia.quantity}")
	private int nickname_validationRuleRepeatCharSugerenciaQuantity;
	@Value("${nickname.validation.rule.whitespace.restrict.enabled}")
	private boolean nickname_validationRuleWhitespaceRestrictEnabled;
	@Value("${nickname.validation.rule.whitespace.sugerencia.enabled}")
	private boolean nickname_validationRuleWhitespaceSugerenciaEnabled;
	@Value("${nickname.validation.rule.username.restrict.enabled}")
	private boolean nickname_validationRuleUsernameRestrictEnabled;
	@Value("${nickname.validation.rule.username.sugerencia.enabled}")
	private boolean nickname_validationRuleUsernameSugerenciaEnabled;
	@Value("${nickname.validation.rule.nickname.restrict.enabled}")
	private boolean nickname_validationRuleNicknameRestrictEnabled;
	@Value("${nickname.validation.rule.nickname.sugerencia.enabled}")
	private boolean nickname_validationRuleNicknameSugerenciaEnabled;
	@Value("${nickname.validation.rule.password.restrict.enabled}")
	private boolean nickname_validationRulePasswordRestrictEnabled;
	@Value("${nickname.validation.rule.password.sugerencia.enabled}")
	private boolean nickname_validationRulePasswordSugerenciaEnabled;
	
	@Value("${password.usuarioregistration.validation.rule.lenght.mandatory.min}")
	private int passwordUsuarioRegistration_validationRuleLenghtMandatoryMin;
	@Value("${password.usuarioregistration.validation.rule.lenght.mandatory.max}")
	private int passwordUsuarioRegistration_validationRuleLenghtMandatoryMax;
	@Value("${password.usuarioregistration.validation.rule.lenght.sugerencia.min.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleLenghtSugerenciaMinEnabled;
	@Value("${password.usuarioregistration.validation.rule.lenght.sugerencia.min}")
	private int passwordUsuarioRegistration_validationRuleLenghtSugerenciaMin;
	@Value("${password.usuarioregistration.validation.rule.uppercase.mandatory.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleUppercaseMandatoryEnabled;
	@Value("${password.usuarioregistration.validation.rule.uppercase.mandatory.quantity}")
	private int passwordUsuarioRegistration_validationRuleUppercaseMandatoryQuantity;
	@Value("${password.usuarioregistration.validation.rule.uppercase.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleUppercaseSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.uppercase.sugerencia.quantity}")
	private int passwordUsuarioRegistration_validationRuleUppercaseSugerenciaQuantity;
	@Value("${password.usuarioregistration.validation.rule.lowercase.mandatory.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleLowercaseMandatoryEnabled;
	@Value("${password.usuarioregistration.validation.rule.lowercase.mandatory.quantity}")
	private int passwordUsuarioRegistration_validationRuleLowercaseMandatoryQuantity;
	@Value("${password.usuarioregistration.validation.rule.lowercase.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleLowercaseSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.lowercase.sugerencia.quantity}")
	private int passwordUsuarioRegistration_validationRuleLowercaseSugerenciaQuantity;
	@Value("${password.usuarioregistration.validation.rule.especialchar.mandatory.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleEspecialcharMandatoryEnabled;
	@Value("${password.usuarioregistration.validation.rule.especialchar.mandatory.quantity}")
	private int passwordUsuarioRegistration_validationRuleEspecialcharMandatoryQuantity;
	@Value("${password.usuarioregistration.validation.rule.especialchar.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleEspecialcharSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.especialchar.sugerencia.quantity}")
	private int passwordUsuarioRegistration_validationRuleEspecialcharSugerenciaQuantity;
	@Value("${password.usuarioregistration.validation.rule.digit.number.mandatory.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleDigitNumberMandatoryEnabled;
	@Value("${password.usuarioregistration.validation.rule.digit.number.mandatory.quantity}")
	private int passwordUsuarioRegistration_validationRuleDigitNumberMandatoryQuantity;
	@Value("${password.usuarioregistration.validation.rule.digit.number.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleDigitNumberSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.digit.number.sugerencia.quantity}")
	private int passwordUsuarioRegistration_validationRuleDigitNumberSugerenciaQuantity;
	@Value("${password.usuarioregistration.validation.rule.repeat.char.restrict.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleRepeatCharRestrictEnabled;
	@Value("${password.usuarioregistration.validation.rule.repeat.char.restrict.quantity}")
	private int passwordUsuarioRegistration_validationRuleRepeatCharRestrictQuantity;
	@Value("${password.usuarioregistration.validation.rule.repeat.char.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleRepeatCharSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.repeat.char.sugerencia.quantity}")
	private int passwordUsuarioRegistration_validationRuleRepeatCharSugerenciaQuantity;
	@Value("${password.usuarioregistration.validation.rule.whitespace.restrict.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleWhitespaceRestrictEnabled;
	@Value("${password.usuarioregistration.validation.rule.whitespace.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleWhitespaceSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.username.restrict.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleUsernameRestrictEnabled;
	@Value("${password.usuarioregistration.validation.rule.username.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleUsernameSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.nickname.restrict.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleNicknameRestrictEnabled;
	@Value("${password.usuarioregistration.validation.rule.nickname.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRuleNicknameSugerenciaEnabled;
	@Value("${password.usuarioregistration.validation.rule.password.restrict.enabled}")
	private boolean passwordUsuarioRegistration_validationRulePasswordRestrictEnabled;
	@Value("${password.usuarioregistration.validation.rule.password.sugerencia.enabled}")
	private boolean passwordUsuarioRegistration_validationRulePasswordSugerenciaEnabled;	
	public SystemGralConf getSystemGralConf(){
		
		if ( systemGralConf == null) {
			buildSystemGralConf();
		}
		return systemGralConf;
	}
	
	private void buildSystemGralConf() {
		SystemGralConf c = new SystemGralConf();
		
		c.setAsymEncrypt(new SGCAsymEncrypt());
		c.getAsymEncrypt().setBits(AsymEncrypt_bits);
		c.getAsymEncrypt().setType(AsymEncrypt_type);
		
		c.setAuth(new SGCAuth());
		c.getAuth().setTokenLenght(SGCAuth_tokenLenght);
		c.getAuth().setTokenType(SGCAuth_tokenType);
		
		c.setRequestId(new MinMaxLenghtDTO());
		c.getRequestId().setMinLenght(requestId_minLenght);
		c.getRequestId().setMaxLenght(requestId_maxLenght);
		
		c.setPersonalAES( new SGCAESSimple() );
		c.getPersonalAES().setBits(personalAES_bits);
		
		c.setExtraAES( new SGCAESSimple() );
		c.getExtraAES().setBits(extraAES_bits);
		c.getExtraAES().setIteration(extraAES_iterationValue);
		
		c.setInvitationAES(new SGCAESDTO());
		c.getInvitationAES().setBits(invitationAes_bits);
		c.getInvitationAES().setIterationMaxValue(invitationAes_iterationMaxValue);
		c.getInvitationAES().setIterationMinValue(invitationAes_iterationMinValue);
		c.getInvitationAES().setKeyMaxLenght(invitationAes_keyMaxLenght);
		c.getInvitationAES().setKeyMinLenght(invitationAes_keyMinLenght);
		c.getInvitationAES().setRandomGeneratorType(invitationAes_randomGeneratorType);
		c.getInvitationAES().setSaltMaxLenght(invitationAes_saltMaxLenght);
		c.getInvitationAES().setSaltMinLenght(invitationAes_saltMinLenght);
		c.getInvitationAES().setRandomGeneratorType(invitationAes_randomGeneratorType);
		
		c.setMessagingAES(new SGCAESDTO());
		c.getMessagingAES().setBits(messagingAes_bits);
		c.getMessagingAES().setIterationMaxValue(messagingAes_iterationMaxValue);
		c.getMessagingAES().setIterationMinValue(messagingAes_iterationMinValue);
		c.getMessagingAES().setKeyMaxLenght(messagingAes_keyMaxLenght);
		c.getMessagingAES().setKeyMinLenght(messagingAes_keyMinLenght);
		c.getMessagingAES().setRandomGeneratorType(messagingAes_randomGeneratorType);
		c.getMessagingAES().setSaltMaxLenght(messagingAes_saltMaxLenght);
		c.getMessagingAES().setSaltMinLenght(messagingAes_saltMinLenght);
		c.getMessagingAES().setRandomGeneratorType(messagingAes_randomGeneratorType);
		
		c.setSessionAES(new SGCAESDTO());
		c.getSessionAES().setBits(sessionAES_bits);
		c.getSessionAES().setIterationMaxValue(sessionAES_iterationMaxValue);
		c.getSessionAES().setIterationMinValue(sessionAES_iterationMinValue);
		c.getSessionAES().setKeyMaxLenght(sessionAES_keyMaxLenght);
		c.getSessionAES().setKeyMinLenght(sessionAES_keyMinLenght);
		c.getSessionAES().setRandomGeneratorType(sessionAES_randomGeneratorType);
		c.getSessionAES().setSaltMaxLenght(sessionAES_saltMaxLenght);
		c.getSessionAES().setSaltMinLenght(sessionAES_saltMinLenght);
		c.getSessionAES().setRandomGeneratorType(sessionAES_randomGeneratorType);

		c.setPublicAES(new SGCAESDTO());
		c.getPublicAES().setBits(publicAES_bits);
		c.getPublicAES().setIterationMaxValue(publicAES_iterationMaxValue);
		c.getPublicAES().setIterationMinValue(publicAES_iterationMinValue);
		c.getPublicAES().setKeyMaxLenght(publicAES_keyMaxLenght);
		c.getPublicAES().setKeyMinLenght(publicAES_keyMinLenght);
		c.getPublicAES().setRandomGeneratorType(publicAES_randomGeneratorType);
		c.getPublicAES().setSaltMaxLenght(publicAES_saltMaxLenght);
		c.getPublicAES().setSaltMinLenght(publicAES_saltMinLenght);
		c.getPublicAES().setRandomGeneratorType(publicAES_randomGeneratorType);		
		
		c.setPrivacityIdAES(new SGCAESDTO());
		c.getPrivacityIdAES().setBits(privacityIdAES_bits);
		c.getPrivacityIdAES().setIterationMaxValue(privacityIdAES_iterationMaxValue);
		c.getPrivacityIdAES().setIterationMinValue(privacityIdAES_iterationMinValue);
		c.getPrivacityIdAES().setKeyMaxLenght(privacityIdAES_keyMaxLenght);
		c.getPrivacityIdAES().setKeyMinLenght(privacityIdAES_keyMinLenght);
		c.getPrivacityIdAES().setRandomGeneratorType(privacityIdAES_randomGeneratorType);
		c.getPrivacityIdAES().setSaltMaxLenght(privacityIdAES_saltMaxLenght);
		c.getPrivacityIdAES().setSaltMinLenght(privacityIdAES_saltMinLenght);
		c.getPrivacityIdAES().setRandomGeneratorType(privacityIdAES_randomGeneratorType);
		
		c.setPrivacityIdAESOn(privacityIdAESOn);
		c.setMessagingWritingMessage(messagingWritingMessage);
		
	
		
		c.setMyAccountConf(new SGCMyAccountConfDTO());
		c.getMyAccountConf().setAudioMaxTimeInSeconds(myAccountConf_audioMaxTimeInSeconds);
//		c.getMyAccountConf().setBlackMessageAttachMandatory(myAccountConf_blackMessageAttachMandatory);
		c.getMyAccountConf().setDownloadAttachAllowImage(myAccountConf_downloadAttachAllowImage);
		c.getMyAccountConf().setHideMyInDetails(myAccountConf_hideMyInDetails);
		c.getMyAccountConf().setHideMyMessageState(myAccountConf_hideMyMessageState);
		c.getMyAccountConf().setResend(myAccountConf_resend);
		c.getMyAccountConf().setTimeMessageAlways(myAccountConf_timeMessageAlways);
		c.getMyAccountConf().setTimeMessageDefaultTime(myAccountConf_timeMessageDefaultTime);
		
		c.getMyAccountConf().setInvitationCode(new SGCInvitationCode());
		
		c.getMyAccountConf().getInvitationCode().setCaseSet(myAccountConf_invitationCode_caseSet);
		c.getMyAccountConf().getInvitationCode().setLenght(new MinMaxLenghtDTO());
		c.getMyAccountConf().getInvitationCode().getLenght().setMaxLenght(myAccountConf_invitationCode_lenght_max);
		c.getMyAccountConf().getInvitationCode().getLenght().setMinLenght(myAccountConf_invitationCode_lenght_min);

		c.setServerInfo(new SGCServerInfo());
		c.getServerInfo().setApiRestUrl(serverInfo_apiRestUrl);
		c.getServerInfo().setName(serverInfo_name);
		c.getServerInfo().setVersion(serverInfo_version);
		c.getServerInfo().setWsUrl(serverInfo_wsUrl);
		
		c.setExtras(new SGCExtrasDTO());
		c.getExtras().setScreenshotEnabled(extrasScreenshotEnabled);
		
		c.getMyAccountConf().setLock(new LockDTO());
		c.getMyAccountConf().getLock().setEnabled(myAccountConf_lock_enabled);
		c.getMyAccountConf().getLock().setSeconds(myAccountConf_lock_seconds);
		c.getMyAccountConf().getLock().setMinSecondsValidation(myAccountConf_lock_min_seconds_validation);
		
		c.setPasswordConfig(new PasswordConfigDTO());
		
		c.getPasswordConfig().setUsuario(new PasswordRulesDTO());
		c.getPasswordConfig().getUsuario().
		setLenghtMandatoryMin(usuario_validationRuleLenghtMandatoryMin).
		setLenghtMandatoryMax(usuario_validationRuleLenghtMandatoryMax).
		setLenghtSugerenciaMinEnabled(usuario_validationRuleLenghtSugerenciaMinEnabled).
		setLenghtSugerenciaMin(usuario_validationRuleLenghtSugerenciaMin).
		setUppercaseMandatoryEnabled(usuario_validationRuleUppercaseMandatoryEnabled).
		setUppercaseMandatoryQuantity(usuario_validationRuleUppercaseMandatoryQuantity).
		setUppercaseSugerenciaEnabled(usuario_validationRuleUppercaseSugerenciaEnabled).
		setUppercaseSugerenciaQuantity(usuario_validationRuleUppercaseSugerenciaQuantity).
		setLowercaseMandatoryEnabled(usuario_validationRuleLowercaseMandatoryEnabled).
		setLowercaseMandatoryQuantity(usuario_validationRuleLowercaseMandatoryQuantity).
		setLowercaseSugerenciaEnabled(usuario_validationRuleLowercaseSugerenciaEnabled).
		setLowercaseSugerenciaQuantity(usuario_validationRuleLowercaseSugerenciaQuantity).
		setEspecialcharMandatoryEnabled(usuario_validationRuleEspecialcharMandatoryEnabled).
		setEspecialcharMandatoryQuantity(usuario_validationRuleEspecialcharMandatoryQuantity).
		setEspecialcharSugerenciaEnabled(usuario_validationRuleEspecialcharSugerenciaEnabled).
		setEspecialcharSugerenciaQuantity(usuario_validationRuleEspecialcharSugerenciaQuantity).
		setDigitNumberMandatoryEnabled(usuario_validationRuleDigitNumberMandatoryEnabled).
		setDigitNumberMandatoryQuantity(usuario_validationRuleDigitNumberMandatoryQuantity).
		setDigitNumberSugerenciaEnabled(usuario_validationRuleDigitNumberSugerenciaEnabled).
		setDigitNumberSugerenciaQuantity(usuario_validationRuleDigitNumberSugerenciaQuantity).
		setRepeatCharRestrictEnabled(usuario_validationRuleRepeatCharRestrictEnabled).
		setRepeatCharRestrictQuantity(usuario_validationRuleRepeatCharRestrictQuantity).
		setRepeatCharSugerenciaEnabled(usuario_validationRuleRepeatCharSugerenciaEnabled).
		setRepeatCharSugerenciaQuantity(usuario_validationRuleRepeatCharSugerenciaQuantity).
		setWhitespaceRestrictEnabled(usuario_validationRuleWhitespaceRestrictEnabled).
		setWhitespaceSugerenciaEnabled(usuario_validationRuleWhitespaceSugerenciaEnabled).
		setUsernameRestrictEnabled(usuario_validationRuleUsernameRestrictEnabled).
		setUsernameSugerenciaEnabled(usuario_validationRuleUsernameSugerenciaEnabled).
		setNicknameRestrictEnabled(usuario_validationRuleNicknameRestrictEnabled).
		setNicknameSugerenciaEnabled(usuario_validationRuleNicknameSugerenciaEnabled).
		setPasswordRestrictEnabled(usuario_validationRulePasswordRestrictEnabled).
		setPasswordSugerenciaEnabled(usuario_validationRulePasswordSugerenciaEnabled);	
		
		c.getPasswordConfig().setNickname(new PasswordRulesDTO());
		c.getPasswordConfig().getNickname().
		setLenghtMandatoryMin(nickname_validationRuleLenghtMandatoryMin).
		setLenghtMandatoryMax(nickname_validationRuleLenghtMandatoryMax).
		setLenghtSugerenciaMinEnabled(nickname_validationRuleLenghtSugerenciaMinEnabled).
		setLenghtSugerenciaMin(nickname_validationRuleLenghtSugerenciaMin).
		setUppercaseMandatoryEnabled(nickname_validationRuleUppercaseMandatoryEnabled).
		setUppercaseMandatoryQuantity(nickname_validationRuleUppercaseMandatoryQuantity).
		setUppercaseSugerenciaEnabled(nickname_validationRuleUppercaseSugerenciaEnabled).
		setUppercaseSugerenciaQuantity(nickname_validationRuleUppercaseSugerenciaQuantity).
		setLowercaseMandatoryEnabled(nickname_validationRuleLowercaseMandatoryEnabled).
		setLowercaseMandatoryQuantity(nickname_validationRuleLowercaseMandatoryQuantity).
		setLowercaseSugerenciaEnabled(nickname_validationRuleLowercaseSugerenciaEnabled).
		setLowercaseSugerenciaQuantity(nickname_validationRuleLowercaseSugerenciaQuantity).
		setEspecialcharMandatoryEnabled(nickname_validationRuleEspecialcharMandatoryEnabled).
		setEspecialcharMandatoryQuantity(nickname_validationRuleEspecialcharMandatoryQuantity).
		setEspecialcharSugerenciaEnabled(nickname_validationRuleEspecialcharSugerenciaEnabled).
		setEspecialcharSugerenciaQuantity(nickname_validationRuleEspecialcharSugerenciaQuantity).
		setDigitNumberMandatoryEnabled(nickname_validationRuleDigitNumberMandatoryEnabled).
		setDigitNumberMandatoryQuantity(nickname_validationRuleDigitNumberMandatoryQuantity).
		setDigitNumberSugerenciaEnabled(nickname_validationRuleDigitNumberSugerenciaEnabled).
		setDigitNumberSugerenciaQuantity(nickname_validationRuleDigitNumberSugerenciaQuantity).
		setRepeatCharRestrictEnabled(nickname_validationRuleRepeatCharRestrictEnabled).
		setRepeatCharRestrictQuantity(nickname_validationRuleRepeatCharRestrictQuantity).
		setRepeatCharSugerenciaEnabled(nickname_validationRuleRepeatCharSugerenciaEnabled).
		setRepeatCharSugerenciaQuantity(nickname_validationRuleRepeatCharSugerenciaQuantity).
		setWhitespaceRestrictEnabled(nickname_validationRuleWhitespaceRestrictEnabled).
		setWhitespaceSugerenciaEnabled(nickname_validationRuleWhitespaceSugerenciaEnabled).
		setUsernameRestrictEnabled(nickname_validationRuleUsernameRestrictEnabled).
		setUsernameSugerenciaEnabled(nickname_validationRuleUsernameSugerenciaEnabled).
		setNicknameRestrictEnabled(nickname_validationRuleNicknameRestrictEnabled).
		setNicknameSugerenciaEnabled(nickname_validationRuleNicknameSugerenciaEnabled).
		setPasswordRestrictEnabled(nickname_validationRulePasswordRestrictEnabled).
		setPasswordSugerenciaEnabled(nickname_validationRulePasswordSugerenciaEnabled);	
		
		c.getPasswordConfig().setPasswordUsuarioRegistration(new PasswordRulesDTO());
		c.getPasswordConfig().getPasswordUsuarioRegistration().
		setLenghtMandatoryMin(passwordUsuarioRegistration_validationRuleLenghtMandatoryMin).
		setLenghtMandatoryMax(passwordUsuarioRegistration_validationRuleLenghtMandatoryMax).
		setLenghtSugerenciaMinEnabled(passwordUsuarioRegistration_validationRuleLenghtSugerenciaMinEnabled).
		setLenghtSugerenciaMin(passwordUsuarioRegistration_validationRuleLenghtSugerenciaMin).
		setUppercaseMandatoryEnabled(passwordUsuarioRegistration_validationRuleUppercaseMandatoryEnabled).
		setUppercaseMandatoryQuantity(passwordUsuarioRegistration_validationRuleUppercaseMandatoryQuantity).
		setUppercaseSugerenciaEnabled(passwordUsuarioRegistration_validationRuleUppercaseSugerenciaEnabled).
		setUppercaseSugerenciaQuantity(passwordUsuarioRegistration_validationRuleUppercaseSugerenciaQuantity).
		setLowercaseMandatoryEnabled(passwordUsuarioRegistration_validationRuleLowercaseMandatoryEnabled).
		setLowercaseMandatoryQuantity(passwordUsuarioRegistration_validationRuleLowercaseMandatoryQuantity).
		setLowercaseSugerenciaEnabled(passwordUsuarioRegistration_validationRuleLowercaseSugerenciaEnabled).
		setLowercaseSugerenciaQuantity(passwordUsuarioRegistration_validationRuleLowercaseSugerenciaQuantity).
		setEspecialcharMandatoryEnabled(passwordUsuarioRegistration_validationRuleEspecialcharMandatoryEnabled).
		setEspecialcharMandatoryQuantity(passwordUsuarioRegistration_validationRuleEspecialcharMandatoryQuantity).
		setEspecialcharSugerenciaEnabled(passwordUsuarioRegistration_validationRuleEspecialcharSugerenciaEnabled).
		setEspecialcharSugerenciaQuantity(passwordUsuarioRegistration_validationRuleEspecialcharSugerenciaQuantity).
		setDigitNumberMandatoryEnabled(passwordUsuarioRegistration_validationRuleDigitNumberMandatoryEnabled).
		setDigitNumberMandatoryQuantity(passwordUsuarioRegistration_validationRuleDigitNumberMandatoryQuantity).
		setDigitNumberSugerenciaEnabled(passwordUsuarioRegistration_validationRuleDigitNumberSugerenciaEnabled).
		setDigitNumberSugerenciaQuantity(passwordUsuarioRegistration_validationRuleDigitNumberSugerenciaQuantity).
		setRepeatCharRestrictEnabled(passwordUsuarioRegistration_validationRuleRepeatCharRestrictEnabled).
		setRepeatCharRestrictQuantity(passwordUsuarioRegistration_validationRuleRepeatCharRestrictQuantity).
		setRepeatCharSugerenciaEnabled(passwordUsuarioRegistration_validationRuleRepeatCharSugerenciaEnabled).
		setRepeatCharSugerenciaQuantity(passwordUsuarioRegistration_validationRuleRepeatCharSugerenciaQuantity).
		setWhitespaceRestrictEnabled(passwordUsuarioRegistration_validationRuleWhitespaceRestrictEnabled).
		setWhitespaceSugerenciaEnabled(passwordUsuarioRegistration_validationRuleWhitespaceSugerenciaEnabled).
		setUsernameRestrictEnabled(passwordUsuarioRegistration_validationRuleUsernameRestrictEnabled).
		setUsernameSugerenciaEnabled(passwordUsuarioRegistration_validationRuleUsernameSugerenciaEnabled).
		setNicknameRestrictEnabled(passwordUsuarioRegistration_validationRuleNicknameRestrictEnabled).
		setNicknameSugerenciaEnabled(passwordUsuarioRegistration_validationRuleNicknameSugerenciaEnabled).
		setPasswordRestrictEnabled(passwordUsuarioRegistration_validationRulePasswordRestrictEnabled).
		setPasswordSugerenciaEnabled(passwordUsuarioRegistration_validationRulePasswordSugerenciaEnabled);
		
		this.systemGralConf = c;
		
	}
	


}
     