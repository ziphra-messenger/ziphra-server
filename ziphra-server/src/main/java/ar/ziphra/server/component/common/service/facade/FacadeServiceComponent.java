package ar.ziphra.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.server.component.grupouserconf.GrupoUserConfService;
import ar.ziphra.server.component.requestid.RequestIdPublicService;
import ar.ziphra.server.encrypt.SessionManagerInfoService;
import ar.ziphra.server.services.protocolomap.ProtocoloMapService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Service
@NoArgsConstructor
@Accessors(fluent = true, chain = false)
@Getter
public class FacadeServiceComponent {
	
	@Autowired @Lazy
	private GrupoUserConfService grupoUserConf;

	@Autowired @Lazy
	private SessionManagerInfoService usuarioSessionInfo;
	
	@Autowired @Lazy
	private RequestIdPublicService requestIdPublic;
	
	@Autowired @Lazy
	private ProtocoloMapService protocoloMap;

	
	
}
