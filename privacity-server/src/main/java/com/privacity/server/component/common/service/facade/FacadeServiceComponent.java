package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.component.grupouserconf.GrupoUserConfService;
import com.privacity.server.component.requestid.RequestIdPublicService;
import com.privacity.server.encrypt.SessionManagerInfoService;
import com.privacity.server.services.protocolomap.ProtocoloMapService;

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
