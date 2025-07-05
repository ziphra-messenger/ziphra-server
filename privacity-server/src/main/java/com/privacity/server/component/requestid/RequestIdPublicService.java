package com.privacity.server.component.requestid;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.adapters.LocalDateAdapter;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.common.exceptions.ValidationException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.commonback.constants.SessionManagerRestConstants;
import com.privacity.commonback.pojo.HealthCheckerPojo;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RequestIdPublicService {
	@Autowired @Lazy
	private FacadeComponent comps;
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	

	@Autowired
	@Lazy
	private HealthCheckerInterface healthChecker;
	


	public void isRequestIdDuplicated(RequestIdDTO halfId)
			throws PrivacityException {
		if ( this.get(halfId.getRequestIdClientSide()) != null) {
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
		}
			
		}	
	
	public RequestIdDTO get(String half) throws PrivacityException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("half", half);

			RequestIdDTO r = rest.postForObject(getServerRI() + SessionManagerRestConstants.REQUEST_ID +SessionManagerRestConstants.REQUEST_ID_PUBLIC_GET, map, RequestIdDTO.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.REQUEST_ID);
			throw new PrivacityException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
	}
	
	public String put(String half, RequestIdDTO requestId) throws PrivacityException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("half", half);
			map.add("requestId",gson.toJson( requestId));
			String r = rest.postForObject(getServerRI() +SessionManagerRestConstants.REQUEST_ID +SessionManagerRestConstants.REQUEST_ID_PUBLIC_ADD, map, String.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.REQUEST_ID);
			throw new PrivacityException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
	}
	public String remove(String half) throws PrivacityException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("half", half);

			String r = rest.postForObject(getServerRI() +SessionManagerRestConstants.REQUEST_ID +SessionManagerRestConstants.REQUEST_ID_PUBLIC_REMOVE, map, String.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.REQUEST_ID);
			throw new PrivacityException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
	}
	
	private String getServerRI() throws PrivacityException {
		HealthCheckerPojo s = healthChecker.getServer(HealthCheckerServerType.REQUEST_ID);
		if (!s.getState().isOnline()) {
			throw new PrivacityException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
		return s.getUrl();
	}
}
