package ar.ziphra.appserver.component.requestid;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.RequestIdDTO;
import ar.ziphra.common.dto.servergralconf.MinMaxLenghtDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j	
public class RequestIdValidationService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	@Value("${usuario.sessioninfo.requestid.expired.seconds}")  
	private int expiredSeconds;

	public RequestIdDTO getNewRequestIdPrivate(RequestIdDTO halfId) throws ZiphraException {
		return getNewRequestIdGeneral(halfId,true);
	}
	public RequestIdDTO getNewRequestIdPublic(RequestIdDTO halfId) throws ZiphraException {
		return getNewRequestIdGeneral(halfId,false);
	}
	
	private RequestIdDTO getNewRequestIdGeneral(RequestIdDTO halfId, boolean isPrivate) throws ZiphraException {
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
						
			comps.service().usuarioSessionInfo().putRequestIdPrivate
			(usuarioLogged.getUsername()
					,halfId.getRequestIdClientSide(),
					
                       serverRequestIdDTO);
		}else {
			comps.service().requestIdPublic().isRequestIdDuplicated(halfId);
			comps.service().requestIdPublic().put(halfId.getRequestIdClientSide(), serverRequestIdDTO);
			

		}
		
		RequestIdDTO r = comps.common().mapper().doitClientRequestIdDTO(serverRequestIdDTO);
		
		return r;
	}

	private void validateRequestIdPeticion(RequestIdDTO halfId) throws ValidationException {
		log.trace("validateRequestIdPeticion: halfId = " + halfId.toString());
		MinMaxLenghtDTO ridC = comps.common().serverConf().getSystemGralConf().getRequestId();
		if( halfId == null){
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_CANT_BE_NULL);
		}
		if ( ( halfId.getRequestIdClientSide().trim().length() < ridC.getMinLenght())
		|| ( halfId.getRequestIdClientSide().trim().length() > ridC.getMaxLenght())) {
			
			log.trace("halfId.getRequestIdClientSide().trim().length(): " + halfId.getRequestIdClientSide().trim().length());
			log.trace(" ridC.getMinLenght(): " +  ridC.getMinLenght());
			log.trace(" ridC.getMaxLenght(): " +  ridC.getMaxLenght());
			
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_BAD_LENGTH);				
		}
		
		LocalDateTime date = LocalDateTime.now();
		date = date.minusSeconds(expiredSeconds);
		
		if (halfId.getDate().isBefore(date)) {
			log.trace("validateRequestIdPeticion: REQUEST_ID_EXPIRED = " + date);
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
		}
	}		
	
 

}
