package com.privacity.server.component.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;

import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerBaseUtil {
	
	@Autowired @Lazy
	protected FacadeComponent comps;
	
	protected final ResponseEntity<String> checkServers(String txtLog, HealthCheckerServerType server, ExceptionReturnCode code ) throws PrivacityException {
		if (!comps.healthChecker().isOnline(server)) {
			log.error(txtLog);
			ProtocoloDTO p = new ProtocoloDTO();
			p.setComponent(ProtocoloComponentsEnum.SERVER_INFO);
			p.setAction(ProtocoloActionsEnum.SERVER_NOT_AVAILABLE);
			p.setCodigoRespuesta(code.name());
			p.setMensajeRespuesta(code.name());
			
			return ResponseEntity.ok().body( comps.util().string().gsonToSend(p));
		}
		return null;
	}
	
	protected final ResponseEntity<String> checkServersSessionManager() throws PrivacityException{
		return checkServers("SESSION_MANAGER_OFFLINE", HealthCheckerServerType.SESSION_MANAGER, ExceptionReturnCode.SESSION_MANAGER_OFFLINE  );
	}
	
	protected final ResponseEntity<String> checkServersRequestId() throws PrivacityException{
		return checkServers("REQUESTID_OFFLINE", HealthCheckerServerType.REQUEST_ID, ExceptionReturnCode.REQUEST_ID_OFFLINE  );
	}
	
	protected final ResponseEntity<String> checkServersMessageId() throws PrivacityException{
		return checkServers("MESSAGEID_OFFLINE", HealthCheckerServerType.IDS_PROVIDER, ExceptionReturnCode.IDS_PROVIDER_OFFLINE  );
	}

}
