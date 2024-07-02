package com.privacity.server.component.grupouserconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.common.model.Grupo;
import com.privacity.server.common.model.GrupoUserConf;
import com.privacity.server.common.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;

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
