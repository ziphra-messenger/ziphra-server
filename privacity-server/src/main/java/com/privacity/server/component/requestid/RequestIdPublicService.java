package com.privacity.server.component.requestid;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.server.common.adapters.LocalDateAdapter;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.ValidationException;

@Service

public class RequestIdPublicService {
	@Autowired @Lazy
	private FacadeComponent comps;
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	
    @Value("${com.privacity.server.requestid.url}")
    private String urlServer;
    
	// TODO pasar a configuracion 
	private final static String CONSTANT_URL = "/entry";

	public void isRequestIdDuplicated(RequestIdDTO halfId)
			throws ValidationException {
		if ( this.get(halfId.getRequestIdClientSide()) != null) {
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
		}
			
		}	
	
	public RequestIdDTO get(String half) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("half", half);

		RequestIdDTO r = rest.postForObject(urlServer + CONSTANT_URL +"/request/id/get", map, RequestIdDTO.class);

		return r;
	}
	
	public String put(String half, RequestIdDTO requestId) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("half", half);
		map.add("requestId",gson.toJson( requestId));
		String r = rest.postForObject(urlServer +CONSTANT_URL +"/request/id/put", map, String.class);

		return r;
	}
	public String remove(String half) {
		RestTemplate rest = new RestTemplate();

		MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
		map.add("half", half);

		String r = rest.postForObject(urlServer +CONSTANT_URL +"/request/id/remove", map, String.class);

		return r;
	}
}
