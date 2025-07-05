package ar.ziphra.server.component.grupouserconf;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.GrupoUserConfDTO;
import ar.ziphra.common.enumeration.RulesConfEnum;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.GrupoUserConf;
import ar.ziphra.core.model.GrupoUserConfId;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GrupoUserConfUtil{
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public GrupoUserConfDTO getGrupoUserConf(Usuario usuarioLogged, Grupo grupo) {
		
		Optional<GrupoUserConf> o = comps.repo().grupoUserConf().findById(new GrupoUserConfId(usuarioLogged, grupo));
		
		if ( o.isPresent() ) {
			return comps.common().mapper().doitGrupoUserConf(o.get());
		}
		
		GrupoUserConfDTO r = new GrupoUserConfDTO();
		r.setIdGrupo(grupo.getIdGrupo()+"");
		return r;
	}
	
	public GrupoUserConf getDefaultGrupoUserConf(Grupo g, Usuario u) {
		GrupoUserConf conf = new GrupoUserConf();
		
		conf.setGrupoUserConfId(new GrupoUserConfId(u,g));; 
		
		conf.setSecretKeyPersonalAlways(RulesConfEnum.NULL);
		conf.setBlackMessageAttachMandatory(RulesConfEnum.NULL);
		conf.setTimeMessageAlways(RulesConfEnum.NULL);
		conf.setAnonimoAlways(RulesConfEnum.NULL);
		conf.setBlockResend(RulesConfEnum.NULL);
		
		conf.setTimeMessageSeconds(300);
		
		conf.setBlackMessageAttachMandatoryReceived(RulesConfEnum.NULL);
		conf.setAnonimoRecived(false);
		conf.setAnonimoRecivedMyMessage(false);
		
conf.setBlockMediaDownload(RulesConfEnum.NULL);
		return conf;

	}
	
}
