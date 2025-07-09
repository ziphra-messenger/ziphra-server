package ar.ziphra.appserver.component.grupoinvitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.core.model.GrupoInvitation;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;

@Service
public class GrupoInvitationUtil {

	@Autowired @Lazy
	private FacadeComponent comps;
	
	public GrupoInvitation getGrupoInvitation(Long idUsuario , Long idGrupo) {
		
		GrupoInvitation r = comps.repo().grupoInvitation().findByGrupoInvitationUsuarioGrupo(idUsuario, idGrupo);
		
		return r;
		
	}
}
