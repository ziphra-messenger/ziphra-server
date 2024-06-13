package com.privacity.server.component.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.LockDTO;
import com.privacity.common.dto.servergralconf.MinMaxLenghtDTO;
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
	@Value("${serverconf.personalAES.iterationMaxValue}")
	private int personalAES_iterationValue;
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
	
	
	
	private SystemGralConf systemGralConf;
	
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
		c.getPersonalAES().setIteration(personalAES_iterationValue);
		
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
		c.getMyAccountConf().setBlackMessageAttachMandatory(myAccountConf_blackMessageAttachMandatory);
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
		
		this.systemGralConf = c;
		
	}
	


}
