package com.privacity.server.component.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.UsuarioDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.exceptions.ProcessException;
import com.privacity.common.exceptions.ValidationException;
import com.privacity.core.model.Grupo;
import com.privacity.core.model.UserForGrupo;
import com.privacity.core.model.UserForGrupoId;
import com.privacity.core.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;

@Service
public class UserUtilService {

	@Autowired @Lazy
	private FacadeComponent comps;



	private Usuario usuarioSystem;

	
	public UserUtilService() throws Exception {
		super();

	}

 
	
//	public String getUsernameLogged() {
//		Authentication auth = SecurityContextHolder
//	            .getContext()
//	            .getAuthentication();
//	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
//	    
//	    return userDetail.getUsername();
//	    
//	}

	
	private Usuario getUsuarioSystemPrivate () throws ProcessException {
		Optional<Usuario> uSystemO = comps.repo().user().findByUsername("SYSTEM");
		
		if ( uSystemO == null || uSystemO.get() == null) {
			throw new ProcessException(ExceptionReturnCode.USER_USER_SYSTEM_NOT_EXISTS); 
		}
		return uSystemO.get();
	}
	public Usuario getUsuarioSystem() throws ProcessException {
		
		if (usuarioSystem == null) {
			usuarioSystem=getUsuarioSystemPrivate();
		}
		return usuarioSystem;
	}

	public UsuarioDTO getUsuarioSystemDTO() throws ProcessException {
		

		return comps.common().mapper().doit(getUsuarioSystemPrivate ());
	
	
	}


	
	public Usuario getUsuarioById(String idUsuario) throws ValidationException {
		return getUsuarioById(Long.parseLong(idUsuario));
	}
	public Usuario getUsuarioById(Long idUsuario) throws ValidationException {
		Optional<Usuario> usuario = comps.repo().user().findById(idUsuario);
		
		if ( usuario == null || !usuario.isPresent()) {
			throw new ValidationException(ExceptionReturnCode.USER_NOT_EXISTS); 
		}
		return usuario.get();
	}
	
	public GrupoRolesEnum getRoleForGrupo(Usuario usuario, Grupo grupo) throws ValidationException {
	    
		Optional<UserForGrupo> ufg = comps.repo().userForGrupo().findById(new UserForGrupoId(usuario, grupo));
		
		if (!ufg.isPresent() ) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);				
		}
		
		return ufg.get().getRole();
	} 
	
	public Usuario getUsuarioForUsername(String usuario) throws ValidationException {
		
		Optional<Usuario> u = comps.repo().user().findByUsername(usuario);
		
		if (!u.isPresent() ) {
			throw new ValidationException(ExceptionReturnCode.USER_NOT_EXISTS);				
		}
		 
		return u.get();
	} 
}
