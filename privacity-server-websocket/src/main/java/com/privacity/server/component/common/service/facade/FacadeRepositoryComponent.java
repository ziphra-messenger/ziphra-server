package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.server.websocket.configuration.UserForGrupoRepository;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class FacadeRepositoryComponent {
	

	@Autowired @Lazy
	
	private UserForGrupoRepository userForGrupo;
	


	
	
	


	public UserForGrupoRepository userForGrupo() {
		return userForGrupo;
	}


	


}
