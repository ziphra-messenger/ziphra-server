package ar.ziphra.appserver.component.requestid;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.RequestIdDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;

@Service
public class RequestIdUtilService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	@Value("${usuario.sessioninfo.requestid.expired.seconds}")  
	private int expiredSeconds;
	
	
	public boolean existsRequestId(RequestIdDTO requestId, boolean isPrivate) throws ZiphraException {
		Usuario usuarioLogged = null;
		if (isPrivate) {
			usuarioLogged = comps.requestHelper().getUsuarioLogged();
		}
		
		
		if(requestId == null) {
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_CANT_BE_NULL);				
		}
		// SE PUEDEN VALIDAR TODOS LOS DEMAS CAMPOS
		// AUNQUE NO ES NECESARIO
		RequestIdDTO r =null;
		if (!isPrivate) {
			r = comps.service().requestIdPublic().get(requestId.getRequestIdClientSide());
		}
		
			
			LocalDateTime date = LocalDateTime.now();
			date = date.minusSeconds(expiredSeconds);
			

			
			if (isPrivate) {
				requestId.setDate(LocalDateTime.now());
				Map map = comps.service().usuarioSessionInfo().getRequestIds(usuarioLogged.getUsername());

				
				if ( map.containsKey(requestId.getRequestIdClientSide())){
					throw new ValidationException(ExceptionReturnCode.REQUEST_ID_CANT_BE_USED_MULTI_TIMES);	
				}
				map.put(requestId.getRequestIdClientSide(), requestId);
				//comps.service().usuarioSessionInfo().get(usuarioLogged.getUsername()).getRequestIds().remove(requestId.getRequestIdClientSide());	
			}else {
				
				if (r.getDate().isBefore(date)) {
					throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
				}
				
				if ( r == null ) {
					throw new ValidationException(ExceptionReturnCode.REQUEST_ID_NOT_EXISTS);
				}
			
				if (requestId.getRequestIdServerSide().equals(r.getRequestIdServerSide())) {
			
						comps.service().requestIdPublic().remove(requestId.getRequestIdClientSide());
			
				}else {
					throw new ValidationException(ExceptionReturnCode.REQUEST_ID_NOT_EXISTS);
				}
			}
			
		
		return true;
	}

	@SuppressWarnings("unlikely-arg-type")
	private void isRequestIdDuplicated(RequestIdDTO halfId, ConcurrentMap<String, RequestIdDTO> map)
			throws ValidationException {
		if ( map.containsKey(halfId)) {
			throw new ValidationException(ExceptionReturnCode.REQUEST_ID_EXPIRED);
		}
	}
}
