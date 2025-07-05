package ar.ziphra.server.component.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.UsuarioDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.exceptions.ProcessException;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.UserForGrupoId;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

@Service
public class UserUtilService {

	@Autowired @Lazy
	private FacadeComponent comps;

	

	private Usuario usuarioSystem;

	private Usuario usuarioAnonimo;
	
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
	
	private Usuario getUsuarioAnonimoPrivate () throws ProcessException {
		Optional<Usuario> uAnonimoO = comps.repo().user().findByUsername("Anonimo");
		
		if ( uAnonimoO == null || uAnonimoO.get() == null) {
			throw new ProcessException(ExceptionReturnCode.USER_USER_SYSTEM_NOT_EXISTS); 
		}
		return uAnonimoO.get();
	}
	public Usuario getUsuarioSystem() throws ProcessException {
		
		if (usuarioSystem == null) {
			usuarioSystem=getUsuarioSystemPrivate();
		}
		return usuarioSystem;
	}

	public Usuario getUsuarioAnonimo() throws ProcessException {
		
		if (usuarioAnonimo == null) {
			usuarioAnonimo=getUsuarioAnonimoPrivate();
		}
		return usuarioAnonimo;
	}
	
	public UsuarioDTO getUsuarioSystemDTO() throws ProcessException {
		

		return comps.common().mapper().doitForGrupo(getUsuarioSystemPrivate ());
	
	
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
