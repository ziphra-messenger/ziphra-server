package com.privacity.messaging.configuration;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.constants.SessionManagerRestConstants;
import com.privacity.core.util.UtilsStringComponent;


/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Service
public class UsuarioSessionInfoService {

	@Value("${com.privacity.server.tasks.healthChecker.serverSessionnanager}")
	private String urlService;

	@Autowired
	@Lazy
	private UtilsStringComponent us;



	private String getServerSM() {
		return urlService;
	}
	@SuppressWarnings("deprecation")
	public String encryptProtocoloWS(String username, ProtocoloDTO p, String url) throws PrivacityException {
		try {

			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, Object > map = new LinkedMultiValueMap < String, Object > ();

			map.add("username", username);
			map.add("url" ,url);
			map.add("p" ,us.gsonToSend(p) );
			HttpHeaders headers = new HttpHeaders();		

			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//			HttpEntity<?> entity = new HttpEntity<Object>(map);

			String url2 = UriComponentsBuilder.fromHttpUrl(getServerSM() +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT_WS ).toUriString();

			return rest.postForObject(url2, map, String.class);


		} catch (RestClientException e) {
			e.printStackTrace();
			throw new PrivacityException(e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public Map<String, String> encryptProtocoloWS(Queue<String> userslist, ProtocoloDTO p, String url) throws PrivacityException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, Object > map = new LinkedMultiValueMap < String, Object > ();

			String[] strObjects = userslist.toArray(new String[0]);

			map.add("usersnamelist", us.gson().toJson( strObjects));
			map.add("url", url);
			map.add("p" ,us.gsonToSend(p) );
			HttpHeaders headers = new HttpHeaders();		

			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

			String url2 = UriComponentsBuilder.fromHttpUrl(getServerSM() +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT +  SessionManagerRestConstants.PROTOCOLO_ENCRYPT_WS_USERLIST ).toUriString();

			@SuppressWarnings("unchecked")
			Map<String, String> r = rest.postForObject(url2, map, Map.class);

			return r;
		} catch (RestClientException e) {
			e.printStackTrace();
			throw new PrivacityException(e.getMessage());
		}

	}


}