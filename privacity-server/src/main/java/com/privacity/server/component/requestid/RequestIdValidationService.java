package com.privacity.server.component.requestid;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.dto.servergralconf.MinMaxLenghtDTO;
import com.privacity.common.dto.servergralconf.SystemGralConf;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.security.Usuario;

@Service

public class RequestIdValidationService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	@Value("${usuario.sessioninfo.requestid.expired.seconds}")  
	private int expiredSeconds;

	public RequestIdDTO getNewRequestIdPrivate(RequestIdDTO halfId) throws ValidationException {
		return getNewRequestIdGeneral(halfId,true);
	}
	public RequestIdDTO getNewRequestIdPublic(RequestIdDTO halfId) throws ValidationException {
		return getNewRequestIdGeneral(halfId,false);
	}
	
	private RequestIdDTO getNewRequestIdGeneral(RequestIdDTO halfId, boolean isPrivate) throws ValidationException {
		Usuario usuarioLogged=null;
		if (isPrivate) {
			usuarioLogged = comps.requestHelper().getUsuarioLogged();	
		}
		
		
		validateRequestIdPeticion(halfId);
		
		RequestIdDTO serverRequestIdDTO = new RequestIdDTO();
		serverRequestIdDTO.setDate(LocalDateTime.now());
		serverRequestIdDTO.setRequestIdServerSide(comps.common().randomGenerator().requestIdServerSide());
		serverRequestIdDTO.setRequestIdClientSide(halfId.getRequestIdClientSide());

		if (isPrivate) {
			// RequestIdDTO> map = comps.service().usuarioSessionInfo().getRequestIds(usuarioLogged.getUsername());
			//isRequestIdDuplicated(halfId, map);
						
			comps.service().usuarioSessionInfo().putRequestId
			(usuarioLogged.getUsername()
					,halfId.getRequestIdClientSide(),
					
                       serverRequestIdDTO);
		}else {
			comps.service().requestIdPublic().isRequestIdDuplicated(halfId);
			comps.service().requestIdPublic().getRequestIdsPublic().put(halfId.getRequestIdClientSide(), serverRequestIdDTO);
			

		}
		
		RequestIdDTO r = comps.common().mapper().doitClientRequestIdDTO(serverRequestIdDTO);
		
		return r;
	}

	private void validateRequestIdPeticion(RequestIdDTO halfId) throws ValidationException {
		
		MinMaxLenghtDTO ridC = comps.common().serverConf().getSystemGralConf().getRequestId();
		if( 
		(halfId == null)
		|| ( halfId.getRequestIdClientSide().trim().length() < ridC.getMinLenght())
		|| ( halfId.getRequestIdClientSide().trim().length() > ridC.getMaxLenght())) {
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_BAD_LENGTH);				
		}
		
		LocalDateTime date = LocalDateTime.now();
		date = date.minusSeconds(expiredSeconds);
		
		if (halfId.getDate().isBefore(date)) {
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
		}
	}		
	
 

}
