package com.privacity.server.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.commonback.common.utils.IdsProviderRestConstants;
import com.privacity.commonback.pojo.HealthCheckerPojo;
import com.privacity.core.interfaces.IdsGeneratorInterface;

import lombok.extern.slf4j.Slf4j;


/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Component("IdsProviderGeneratorRest")
@Slf4j
class  IdsProviderGeneratorRest  implements IdsGeneratorInterface {

	@Autowired
	@Lazy
	private HealthCheckerInterface healthChecker;
	
      public Long getNextMessageId(Long idGrupo) throws PrivacityException {    	

    	  try {
    	  RestTemplate rest = new RestTemplate();

    	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    	 // map.add("idGrupo", "idGrupo=");


    	  Long idMessage = rest.postForObject(getServerMI() + IdsProviderRestConstants.GENERATOR
    			  + IdsProviderRestConstants.GENERATOR_GET_NEXT_MESSAGE_ID +"/"
    			  + idGrupo, map, Long.class);
    	  
        return idMessage;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.IDS_PROVIDER);
			throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_OFFLINE);
		} catch (PrivacityException e) {
			healthChecker.alertOffLine(HealthCheckerServerType.IDS_PROVIDER);
			throw new PrivacityException(e);
		}

    }
      
  	private String getServerMI() throws PrivacityException {
		HealthCheckerPojo s = healthChecker.getServer(HealthCheckerServerType.IDS_PROVIDER);
		if (!s.getState().isOnline()) {
			throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_OFFLINE);
		}
		return s.getUrl();
	}

	@Override
	public Long getNextGrupoId() throws PrivacityException {
		log.debug("entrada getNextUsuarioId ");
		return restCall(IdsProviderRestConstants.GENERATOR_GET_NEXT_GRUPO_ID);
	}

	@Override
	public Long getNextUsuarioId() throws PrivacityException {
		log.debug("entrada getNextUsuarioId ");
		return restCall(IdsProviderRestConstants.GENERATOR_GET_NEXT_USUARIO_ID);
	}

	@Override
	public Long getNextAESId() throws PrivacityException {
		log.debug("entrada getNextAESId ");
		return restCall(IdsProviderRestConstants.GENERATOR_GET_NEXT_AES_ID);
	}

	@Override
	public Long getNextEncryptKeysId() throws PrivacityException {
		log.debug("entrada getNextEncryptKeysId ");
		return restCall(IdsProviderRestConstants.GENERATOR_GET_NEXT_ENCRYPT_KEYS_ID);
	}
  	
    private Long restCall(String restEndpoint) throws PrivacityException {    	

  	  try {
  	  RestTemplate rest = new RestTemplate();

  	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
  	 // map.add("idGrupo", "idGrupo=");


  	  Long idMessage = rest.postForObject(getServerMI() + IdsProviderRestConstants.GENERATOR
  			  + restEndpoint, map, Long.class);
  	log.debug("Id Obtenido: "+ idMessage);
      return idMessage;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.IDS_PROVIDER);
			throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_OFFLINE);
		} catch (PrivacityException e) {
			healthChecker.alertOffLine(HealthCheckerServerType.IDS_PROVIDER);
			throw new PrivacityException(e);
		}

  }

	@Override
	public void refresh() throws PrivacityException {
		log.error(ExceptionReturnCode.IDS_PROVIDER_REFRESH_MUST_NOT_BE_CALLED_IN_THIS_CASE.toShow("Solo se refresca las llamada rest por el health checker ping"));
		throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_REFRESH_MUST_NOT_BE_CALLED_IN_THIS_CASE);
	}

	@Override
	public Map<Long, String> getRandomNicknameByGrupo(String idGrupo) throws PrivacityException {
   	

	    	  try {
	    	  RestTemplate rest = new RestTemplate();

	    	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	    	 // map.add("idGrupo", "idGrupo=");


	    	  Map<Long, String> idMessage = rest.postForObject(getServerMI() + IdsProviderRestConstants.RANDOM
	    			  + IdsProviderRestConstants.RANDOM_NICKNAME_GET_BY_GRUPO +"/"
	    			  + idGrupo, map,  Map.class);
	    	  
	        return idMessage;
			} catch (RestClientException e) {
				log.error(e.getMessage());
				healthChecker.alertOffLine(HealthCheckerServerType.IDS_PROVIDER);
				throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_OFFLINE);
			} catch (PrivacityException e) {
				healthChecker.alertOffLine(HealthCheckerServerType.IDS_PROVIDER);
				throw new PrivacityException(e);
			}

	    
	}

//      public static void main(String[] args) throws Exception {
//
//Random rand = new Random();
//
//// Obtain a number between [0 - 49].
//
//
//    	  MessageIdRest m = new MessageIdRest();
//    	  while( true) {
//    		 System.out.println(m.getNextMessageId(  new Long(  rand.nextInt(6) )));
//    		 Thread.sleep(2000);
//    	  }
//	}

}