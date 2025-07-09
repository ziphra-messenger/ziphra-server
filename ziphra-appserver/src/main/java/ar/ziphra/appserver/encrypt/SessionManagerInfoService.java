package ar.ziphra.appserver.encrypt;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ar.ziphra.common.dto.AESAllDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.dto.RequestIdDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.enumeration.HealthCheckerServerType;
import ar.ziphra.commonback.common.interfaces.HealthCheckerInterface;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.commonback.pojo.HealthCheckerPojo;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Service
@Slf4j
public class SessionManagerInfoService {
    

	
	@Autowired
	@Lazy
	private HealthCheckerInterface healthChecker;

	@Autowired @Lazy
	protected FacadeComponent comps;
	
	public Map getRequestIds(String username) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);

			Map r = rest.postForObject(getServerRI() + SessionManagerRestConstants.REQUEST_ID   + SessionManagerRestConstants.REQUEST_ID_PRIVATE_GET_ALL, map, Map.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.REQUEST_ID);
			throw new ZiphraException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
	}

	public AESAllDTO getAesDtoAll(String username) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);

			AESAllDTO r = rest.postForObject(getServerSM() + SessionManagerRestConstants.SESSION +SessionManagerRestConstants.SESSION_GET_ALL, map, AESAllDTO.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
		
	}
	public void remove(String username) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);

			rest.postForObject(getServerSM() + SessionManagerRestConstants.SESSION +SessionManagerRestConstants.SESSION_REMOVE, map, Object.class);
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}

	}

	public String decryptSessionAESServerIn(String username, String obj, String className) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);
			map.add("obj", obj);
			map.add("className", className);

			String r = rest.postForObject(getServerSM() + SessionManagerRestConstants.DECRYPT+ SessionManagerRestConstants.DECRYPT_SERVER_IN, map, String.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
	}

	public ProtocoloDTO decryptProtocolo(String username, String obj, String url) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);
			map.add("obj", obj);
			map.add("url", url);

			ProtocoloDTO r = rest.postForObject(getServerSM() +  SessionManagerRestConstants.PROTOCOLO_DECRYPT +  SessionManagerRestConstants.PROTOCOLO_DECRYPT_SERVER_IN, map, ProtocoloDTO.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
	}
	public String encryptProtocolo(String username,  ProtocoloDTO p, String url) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, Object > map = new LinkedMultiValueMap < String, Object > ();
			
			map.add("username", username);
			map.add("url" ,url);
			map.add("p" , comps.util().string().gsonToSend(p) );
			HttpHeaders headers = new HttpHeaders();		

			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<?> entity = new HttpEntity<Object>(map);
			
			String url2 = UriComponentsBuilder.fromHttpUrl(getServerSM() +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT_SERVER_OUT ).toUriString();
			
			return rest.postForObject(url2, map, String.class);
			
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
	}

	public String encryptSessionAESServerOut(String username, String obj) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);
			map.add("obj", obj);

			String r = rest.postForObject(getServerSM() +  SessionManagerRestConstants.ENCRYPT +  SessionManagerRestConstants.ENCRYPT_SERVER_OUT   , map, String.class);


			return r;
		} catch (RestClientException e) {
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			e.printStackTrace();
			throw new ZiphraException(e.getMessage());
		}
	}
//	public String encryptSessionAESServerWS(String username, String obj) {
//		RestTemplate rest = new RestTemplate();
//
//		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
//		map.add("username", username);
//		map.add("obj", obj);
//
//		String r = rest.postForObject(CONSTANT_URL +"/encryption/encrypt/sessionAESServerWS", map, String.class);
//
//		return r;
//	}
	public Object ziphraIdServiceDecrypt(String username, Object dtoObject, String className) throws Exception, ClassNotFoundException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);
			map.add("obj", comps.util().string().gsonToSend(dtoObject));
			map.add("className", className);

			Object r = rest.postForObject(getServerSM() + SessionManagerRestConstants.DECRYPT +SessionManagerRestConstants.DECRYPT_IDS, map, Class.forName(className));

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
	}

	public String ziphraIdServiceEncrypt(String username, Object dtoObject, String className) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);
			map.add("obj", comps.util().string().gsonToSend(dtoObject));
			map.add("className", className);

			String r = rest.postForObject(getServerSM() + SessionManagerRestConstants.ENCRYPT+ SessionManagerRestConstants.ENCRYPT_IDS, map, String.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
	}
	public Object ziphraIdServiceEncrypt2(String username, Object dtoObject, String className) throws ZiphraException{
		try {
			if (className.equals("void")) return null;
			
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);
			map.add("obj", comps.util().string().gsonToSend(dtoObject));
			map.add("className", className);

			Object r = rest.postForObject(getServerSM() + SessionManagerRestConstants.ENCRYPT+ SessionManagerRestConstants.ENCRYPT_IDS, map, Class.forName(className));

			return r;
		} catch (Exception e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
	}
	public void putRequestIdPrivate(String username, String requestIdClientSide, RequestIdDTO serverRequestIdDTO) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("username", username);
			map.add("requestIdClientSide", requestIdClientSide);
			map.add("serverRequestIdDTO", comps.util().string().gsonToSend(serverRequestIdDTO));

			rest.postForObject(getServerRI() + SessionManagerRestConstants.REQUEST_ID +SessionManagerRestConstants.REQUEST_ID_PRIVATE_ADD, map, Object.class);
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.REQUEST_ID);
			throw new ZiphraException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}

	}

	private String getServerRI() throws ZiphraException {
		HealthCheckerPojo s = healthChecker.getServer(HealthCheckerServerType.REQUEST_ID);
		if (!s.getState().isOnline()) {
			throw new ZiphraException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
		return s.getUrl();
	}
	
	private String getServerSM() throws ZiphraException {
		HealthCheckerPojo s = healthChecker.getServer(HealthCheckerServerType.SESSION_MANAGER);
		if (!s.getState().isOnline()) {
			throw new ZiphraException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
		}
		return s.getUrl();
	}
	
//	public static void main(String[] args) throws Exception {
//		RestTemplate rest = new RestTemplate();
//		UtilsStringService u = new UtilsStringService();
//
//		ProtocoloDTO p = new ProtocoloDTO();
//		p.setAction(ProtocoloActionsEnum.AUTH_LOGIN);
//		p.setComponent(ProtocoloComponentsEnum.AUTH);
//		MultiValueMap < String, Object > map = new LinkedMultiValueMap < String, Object > ();
//		
//		map.add("username", "JORGE");
//		map.add("url" ,"CONSTANT_URL_PATH_PRIVATE_SEND");
//		map.add("p" , u.gsonToSend(p) );
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		
//		rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//		String url2 = UriComponentsBuilder.fromHttpUrl("http://localhost:8085" +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT_SERVER_OUT )
////				 .queryParam("url" ,"CONSTANT_URL_PATH_PRIVATE_SEND")
////		         .queryParam("username" ,"JORGE")
////		         .queryParam("p" , u.gsonToSend(p ))
//		         .toUriString();
//		
//		String r = rest.postForObject(url2,map,String.class);
//
//	}
}