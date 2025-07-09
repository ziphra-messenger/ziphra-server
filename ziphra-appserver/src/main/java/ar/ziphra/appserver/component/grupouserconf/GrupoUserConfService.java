package ar.ziphra.appserver.component.grupouserconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.GrupoUserConf;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class GrupoUserConfService{
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public GrupoUserConf saveDefaultGrupoUserConf(Grupo g, Usuario u) {
		
		GrupoUserConf grupoUserConf = comps.util().grupoUserConf().getDefaultGrupoUserConf(g,u);
		comps.repo().grupoUserConf().save(grupoUserConf);
		
		return grupoUserConf;

	}
	
}
