package com.privacity.server.component.common.service;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.servergralconf.MinMaxLenghtDTO;
import com.privacity.common.dto.servergralconf.SGCAESDTO;
import com.privacity.common.dto.servergralconf.SGCInvitationCode;
import com.privacity.common.enumeration.RandomGeneratorType;
import com.privacity.common.util.RandomGenerator;

@Service
public class RandomGeneratorService {


	
	@Value("${random_generator.id_message.lenght}")
	private int idMessageLenght;
//	
//	@Value("${random_generator.invitation_code.lenght}")
//	private int invitationCodeLenght;

	@Value("${random_generator.nickname.alphabetic.lenght}")
	private int nicknameAlphabeticLenght;

	@Value("${random_generator.nickname.numeric.lenght}")
	private int nicknameNumericLenght;

//	@Value("${random_generator.session.aes.secret.key.lenght}")
//	private int sessionAesSecretKeyLenght;
//	
//	@Value("${random_generator.session.aes.secret.salt.lenght}")
//	private int sessionAesSecretSaltLenght;
//
//	@Value("${random_generator.session.aes.iteration.min}")
//	private int sessionAesIterationMin;
//
//	@Value("${random_generator.session.aes.iteration.max}")
//	private int sessionAesIterationMax;	
//	
//	@Value("${random_generator.privacityid.aes.secret.key.min.lenght}")
//	private int privacityIdAesSecretKeyMinLenght;
//	
//	@Value("${random_generator.privacityid.aes.secret.salt.min.lenght}")
//	private int privacityIdAesSecretSaltMinLenght;
//	
//	@Value("${random_generator.privacityid.aes.secret.key.max.lenght}")
//	private int privacityIdAesSecretKeyMaxLenght;
//	
//	@Value("${random_generator.privacityid.aes.secret.salt.max.lenght}")
//	private int privacityIdAesSecretSaltMaxLenght;	
//	
//	@Value("${random_generator.jwt.token.lenght}")
//	private int jwtTokenLenght;	
	private ServerConfService conf;
	
	public RandomGeneratorService(	@Autowired
	ServerConfService conf) {
		this.conf = conf;
	}
//	public Long idMessage() {
//		return Long.parseLong ((new Date().getTime()+"") + RandomStringUtils.randomNumeric(idMessageLenght));
//	}
//	
	public String invitationCode() {
		SGCInvitationCode a = conf.getSystemGralConf().getMyAccountConf().getInvitationCode();
	    return RandomGenerator.generate(RandomGeneratorType.ALPHANUMERIC, a.getLenght().getMinLenght(), a.getLenght().getMaxLenght()); 
	}
	
	public String alias() {
		return RandomStringUtils.randomAlphabetic(nicknameAlphabeticLenght).toUpperCase() + "-" +
				RandomStringUtils.randomNumeric(nicknameNumericLenght); 
	}
	
	public String sessionAesSecretKey() {
		SGCAESDTO a = conf.getSystemGralConf().getSessionAES();
	    return "AA" ; // RandomGenerator.AESKey(a); 
	}

	public String sessionAesSecretSalt() {
		SGCAESDTO a = conf.getSystemGralConf().getSessionAES();
		return "AA" ; // return RandomGenerator.AESSalt(a); 

	}	
	
	public int sessionAesIteration() {
		SGCAESDTO a = conf.getSystemGralConf().getSessionAES();
		return 1 ; // return RandomGenerator.AESIteration(a); 
	}		
	
	public String privacityIdAesSecretKey() {
		SGCAESDTO a = conf.getSystemGralConf().getPrivacityIdAES();
	    return RandomGenerator.AESKey(a);
	}

	public String privacityIdAesSecretSalt() {
		SGCAESDTO a = conf.getSystemGralConf().getPrivacityIdAES();
	    return RandomGenerator.AESSalt(a);
	}
	
	public int privacityIdAesIteration() {
		SGCAESDTO a = conf.getSystemGralConf().getPrivacityIdAES();
	    return RandomGenerator.AESIteration(a);

	}		


	public String jwtSecret() {
		return RandomStringUtils.randomAscii(conf.getSystemGralConf().getAuth().getTokenLenght());
	}
	
	public String requestIdServerSide() {
		MinMaxLenghtDTO a = conf.getSystemGralConf().getRequestId();
	    return RandomGenerator.generate(RandomGeneratorType.NUMERIC, a.getMinLenght(), a.getMaxLenght());
	}
	
	public Long idGrupoInvitation() {
		return Long.parseLong ((new Date().getTime()+"") + RandomStringUtils.randomNumeric(6));
	}	
}
