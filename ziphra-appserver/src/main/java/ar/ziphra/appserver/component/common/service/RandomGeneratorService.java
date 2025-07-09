package ar.ziphra.appserver.component.common.service;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.servergralconf.MinMaxLenghtDTO;
import ar.ziphra.common.dto.servergralconf.SGCAESDTO;
import ar.ziphra.common.dto.servergralconf.SGCInvitationCode;
import ar.ziphra.common.enumeration.RandomGeneratorType;
import ar.ziphra.common.util.RandomGenerator;

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
//	@Value("${random_generator.ziphraid.aes.secret.key.min.lenght}")
//	private int ziphraIdAesSecretKeyMinLenght;
//	
//	@Value("${random_generator.ziphraid.aes.secret.salt.min.lenght}")
//	private int ziphraIdAesSecretSaltMinLenght;
//	
//	@Value("${random_generator.ziphraid.aes.secret.key.max.lenght}")
//	private int ziphraIdAesSecretKeyMaxLenght;
//	
//	@Value("${random_generator.ziphraid.aes.secret.salt.max.lenght}")
//	private int ziphraIdAesSecretSaltMaxLenght;	
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
	
	public String ziphraIdAesSecretKey() {
		SGCAESDTO a = conf.getSystemGralConf().getZiphraIdAES();
	    return RandomGenerator.AESKey(a);
	}

	public String ziphraIdAesSecretSalt() {
		SGCAESDTO a = conf.getSystemGralConf().getZiphraIdAES();
	    return RandomGenerator.AESSalt(a);
	}
	
	public int ziphraIdAesIteration() {
		SGCAESDTO a = conf.getSystemGralConf().getZiphraIdAES();
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
