package com.privacity.server.component.requestid;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.server.common.exceptions.ValidationException;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.Getter;

@Service
@Getter
public class RequestIdPublicService {
	@Autowired @Lazy
	private FacadeComponent comps;
	
	private ConcurrentMap<String,RequestIdDTO> requestIdsPublic = new ConcurrentHashMap<String,RequestIdDTO>();

	public void isRequestIdDuplicated(RequestIdDTO halfId)
		throws ValidationException {
	if ( requestIdsPublic.containsKey(halfId)) {
		throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
	}
		
	}	

}
