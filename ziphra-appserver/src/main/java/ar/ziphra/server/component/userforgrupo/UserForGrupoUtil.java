package ar.ziphra.server.component.userforgrupo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

@Service
@NoArgsConstructor
@Log
public class UserForGrupoUtil {

	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public UserForGrupo getValidation(Usuario u, long idGrupo ) throws ValidationException {
		
		UserForGrupo ufgO = comps.repo().userForGrupo().findByIdPrimitive(idGrupo, u.getIdUser());
		
		if ( ufgO == null ) {
			log.severe(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO.name() + " usuario: " + u.getIdUser() + " grupo: " + idGrupo);
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);	
		}
		
		return ufgO;
	}
	
	public UserForGrupo getValidationGetAll(Usuario u, long idGrupo ) throws ValidationException {
		
		UserForGrupo ufgO = comps.repo().userForGrupo().findByIdPrimitive(idGrupo, u.getIdUser());
		
		if ( ufgO == null ) {
			log.severe(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO.name() + " usuario: " + u.getIdUser() + " grupo: " + idGrupo);
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);	
		}
		
		return ufgO;
	}
	public Usuario puedeEnviarMensaje(String u, Grupo g, List<UserForGrupo> usersForGrupo ) throws ValidationException {
		
		
		for (UserForGrupo ufg : usersForGrupo) {
			if (ufg.getUserForGrupoId().getUser().getUsername().equals(u)) {
				
				if (ufg.getRole().equals(GrupoRolesEnum.READONLY)) {
					log.severe(GrupoRolesEnum.READONLY.name() + " usuario: " + ufg.getUserForGrupoId().getUser().getIdUser() + " grupo: " + g.getIdGrupo());
					return null;
				}
				
				return ufg.getUserForGrupoId().getUser();
			}
		}
		log.severe(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO.name() + " usuario: " + u + " grupo: " + g.getIdGrupo());
		throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);
		

	}
	
//	public String getNicknameForGrupo(Grupo g, Usuario u) {
//		
//		Optional<UserForGrupo> ufcO = comps.repo().userForGrupo().findById(new UserForGrupoId(u, g));
//		
//		if (!ufcO.isPresent()) return u.getNickname();
//		
//		if ( ufcO.get().getNicknameGrupo() == null ) {
//			return u.getNickname();
//		}
//		return ufcO.get().getNicknameGrupo();
//			
//	}


	
	
}
