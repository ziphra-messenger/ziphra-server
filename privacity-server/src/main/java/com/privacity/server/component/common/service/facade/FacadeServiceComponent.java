package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.component.grupouserconf.GrupoUserConfService;
import com.privacity.server.component.requestid.RequestIdPublicService;
import com.privacity.server.encrypt.UsuarioSessionInfoService;
import com.privacity.server.services.protocolomap.ProtocoloMapService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FacadeServiceComponent {
	
	@Autowired @Lazy
	private GrupoUserConfService grupoUserConf;

	@Autowired @Lazy
	private UsuarioSessionInfoService usuarioSessionInfo;
	
	@Autowired @Lazy
	private RequestIdPublicService requestIdPublic;
	
	@Autowired @Lazy
	private ProtocoloMapService protocoloMap;

	
	public RequestIdPublicService requestIdPublic() {
		return requestIdPublic;
	}
	public GrupoUserConfService grupoUserConf() {
		return grupoUserConf;
	}
	public UsuarioSessionInfoService usuarioSessionInfo() {
		return usuarioSessionInfo;
	}
	public ProtocoloMapService protocoloMap() {
		return protocoloMap;
	}
	
	
}
