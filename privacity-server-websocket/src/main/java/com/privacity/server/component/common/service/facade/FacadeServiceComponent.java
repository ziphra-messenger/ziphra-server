package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.websocket.configuration.UsuarioSessionInfoService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FacadeServiceComponent {
	


	@Autowired @Lazy
	private UsuarioSessionInfoService usuarioSessionInfo;
	

	public UsuarioSessionInfoService usuarioSessionInfo() {
		return usuarioSessionInfo;
	}

	
}
