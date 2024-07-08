package com.privacity.server.encrypt;
import java.time.LocalDateTime;
import java.util.Map;

import org.hibernate.mapping.TableOwner;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.dto.AESAllDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.server.util.LocalDateAdapter;

/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Service
public class UsuarioSessionInfoService {

	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	
	// TODO pasar a configuracion 
	private final static String CONSTANT_URL = "http://localhost:8085/entry";

	public Map getRequestIds(String username) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);

		Map r = rest.postForObject(CONSTANT_URL +"/requestId/getAll", map, Map.class);

		return r;
	}

	public AESAllDTO getAesDtoAll(String username) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);

		AESAllDTO r = rest.postForObject(CONSTANT_URL +"/get/AesDtoAll", map, AESAllDTO.class);

		return r;
	}
	public void remove(String username) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);

		rest.postForObject(CONSTANT_URL +"/remove/session", map, Object.class);

	}

	public String decryptSessionAESServerIn(String username, String obj, String className) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", obj);
		map.add("className", className);

		String r = rest.postForObject(CONSTANT_URL +"/encryption/decrypt/sessionAESServerIn", map, String.class);

		return r;
	}

	public ProtocoloDTO decryptProtocolo(String username, String obj, String url) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", obj);
		map.add("url", url);

		ProtocoloDTO r = rest.postForObject(CONSTANT_URL +"/encryption/decrypt/protocolo", map, ProtocoloDTO.class);

		return r;
	}
	public String encryptProtocolo(String username, String obj, String url) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", obj);
		map.add("url", url);

		String r = rest.postForObject(CONSTANT_URL +"/encryption/encrypt/protocolo", map, String.class);

		return r;
	}
	public String encryptProtocoloWS(String username, Object obj, String url) {
		return encryptProtocoloWS(username, gson.toJson(obj), url);
	}
	public String encryptProtocoloWS(String username, String obj, String url) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", obj);
		map.add("url", url);

		String r = rest.postForObject(CONSTANT_URL +"/encryption/encrypt/protocoloWS", map, String.class);

		return r;
	}

	public String encryptSessionAESServerOut(String username, String obj) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", obj);

		String r = rest.postForObject(CONSTANT_URL +"/encryption/encrypt/sessionAESServerOut", map, String.class);

		return r;
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
	public Object privacityIdServiceDecrypt(String username, Object dtoObject, String className) throws Exception, ClassNotFoundException {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", gson.toJson(dtoObject));
		map.add("className", className);

		Object r = rest.postForObject(CONSTANT_URL +"/encryption/decrypt/ids", map, Class.forName(className));

		return r;
	}

	public String privacityIdServiceEncrypt(String username, Object dtoObject, String className) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", gson.toJson(dtoObject));
		map.add("className", className);

		String r = rest.postForObject(CONSTANT_URL +"/encryption/encrypt/ids", map, String.class);

		return r;
	}
	public Object privacityIdServiceEncrypt2(String username, Object dtoObject, String className) throws Exception, ClassNotFoundException {
		if (className.equals("void")) return null;
		
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("obj", gson.toJson(dtoObject));
		map.add("className", className);

		Object r = rest.postForObject(CONSTANT_URL +"/encryption/encrypt/ids", map, Class.forName(className));

		return r;
	}
	public void putRequestId(String username, String requestIdClientSide, RequestIdDTO serverRequestIdDTO) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("username", username);
		map.add("username", requestIdClientSide);
		map.add("serverRequestIdDTO", gson.toJson(serverRequestIdDTO));

		rest.postForObject(CONSTANT_URL +"/requestId/add", map, Object.class);

	}

}