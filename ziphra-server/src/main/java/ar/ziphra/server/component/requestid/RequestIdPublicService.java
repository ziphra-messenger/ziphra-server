package ar.ziphra.server.component.requestid;

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
import ar.ziphra.common.adapters.LocalDateAdapter;
import ar.ziphra.common.dto.RequestIdDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.commonback.common.enumeration.HealthCheckerServerType;
import ar.ziphra.commonback.common.interfaces.HealthCheckerInterface;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.commonback.pojo.HealthCheckerPojo;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

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
			throws ZiphraException {
		if ( this.get(halfId.getRequestIdClientSide()) != null) {
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
		}
			
		}	
	
	public RequestIdDTO get(String half) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("half", half);

			RequestIdDTO r = rest.postForObject(getServerRI() + SessionManagerRestConstants.REQUEST_ID +SessionManagerRestConstants.REQUEST_ID_PUBLIC_GET, map, RequestIdDTO.class);

			return r;
		} catch (RestClientException e) {
			log.error(e.getMessage());
			healthChecker.alertOffLine(HealthCheckerServerType.REQUEST_ID);
			throw new ZiphraException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
	}
	
	public String put(String half, RequestIdDTO requestId) throws ZiphraException {
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
			throw new ZiphraException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
		}
	}
	public String remove(String half) throws ZiphraException {
		try {
			RestTemplate rest = new RestTemplate();

			MultiValueMap < String, String > map = new LinkedMultiValueMap < String, String > ();
			map.add("half", half);

			String r = rest.postForObject(getServerRI() +SessionManagerRestConstants.REQUEST_ID +SessionManagerRestConstants.REQUEST_ID_PUBLIC_REMOVE, map, String.class);

			return r;
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
}
