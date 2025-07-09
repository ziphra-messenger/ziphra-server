package ar.ziphra.appserver.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.appserver.component.auth.AuthValidationService;
import ar.ziphra.appserver.component.grupo.GrupoValidationService;
import ar.ziphra.appserver.component.message.MessageValidationService;
import ar.ziphra.appserver.component.requestid.RequestIdValidationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Component
@NoArgsConstructor
@Accessors(fluent = true, chain = false)
@Getter
public class FacadeValidationService {
	@Autowired @Lazy
	private AuthValidationService auth;

	@Autowired @Lazy
	private RequestIdValidationService requestId;

	@Autowired @Lazy
	private GrupoValidationService grupo;
	
	@Autowired @Lazy
	private MessageValidationService message;
	


}
