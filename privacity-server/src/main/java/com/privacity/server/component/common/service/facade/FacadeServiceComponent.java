package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.component.grupouserconf.GrupoUserConfService;
import com.privacity.server.component.requestid.RequestIdPublicService;
import com.privacity.server.session.UsuarioSessionInfoService;

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
	
	public RequestIdPublicService requestIdPublic() {
		return requestIdPublic;
	}
	public GrupoUserConfService grupoUserConf() {
		return grupoUserConf;
	}
	public UsuarioSessionInfoService usuarioSessionInfo() {
		return usuarioSessionInfo;
	}
	

	
}
