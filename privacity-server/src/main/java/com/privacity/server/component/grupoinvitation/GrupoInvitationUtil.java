package com.privacity.server.component.grupoinvitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.core.model.GrupoInvitation;
import com.privacity.server.component.common.service.facade.FacadeComponent;

@Service
public class GrupoInvitationUtil {

	@Autowired @Lazy
	private FacadeComponent comps;
	
	public GrupoInvitation getGrupoInvitation(Long idUsuario , Long idGrupo) {
		
		GrupoInvitation r = comps.repo().grupoInvitation().findByGrupoInvitationUsuarioGrupo(idUsuario, idGrupo);
		
		return r;
		
	}
}
