package com.privacity.server.component.grupouserconf;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.GrupoUserConfDTO;
import com.privacity.common.enumeration.GrupoUserConfEnum;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.GrupoUserConf;
import com.privacity.server.model.GrupoUserConfId;
import com.privacity.server.security.Usuario;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GrupoUserConfUtil{
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public GrupoUserConfDTO getGrupoUserConf(Usuario usuarioLogged, Grupo grupo) {
		
		Optional<GrupoUserConf> o = comps.repo().grupoUserConf().findById(new GrupoUserConfId(usuarioLogged, grupo));
		
		//if ( o.isPresent() ) {
			return comps.common().mapper().doit(o.get());
		//}
		
		//GrupoUserConfDTO r = new GrupoUserConfDTO();
		//r.setIdGrupo(grupo.getIdGrupo()+"");
		//return r;
	}
	
	public GrupoUserConf getDefaultGrupoUserConf(Grupo g, Usuario u) {
		GrupoUserConf conf = new GrupoUserConf();
		
		conf.setGrupoUserConfId(new GrupoUserConfId(u,g));; 

		conf.setSecretKeyPersonalAlways(GrupoUserConfEnum.GRUPO_FALSE);
		conf.setBlackMessageAlways(GrupoUserConfEnum.GRUPO_FALSE);
		conf.setTimeMessageAlways(GrupoUserConfEnum.GRUPO_FALSE);
		conf.setAnonimoAlways(GrupoUserConfEnum.GRUPO_FALSE);
		conf.setPermitirReenvio(GrupoUserConfEnum.GRUPO_TRUE);
		
		conf.setTimeMessageSeconds(300);
		
		conf.setBlackMessageRecived(GrupoUserConfEnum.GRUPO_FALSE);
		conf.setAnonimoRecived(GrupoUserConfEnum.GRUPO_FALSE);

		
		return conf;

	}
	
}
