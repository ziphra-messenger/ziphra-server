package ar.ziphra.core.util;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.exceptions.ProcessException;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.UserForGrupoId;
import ar.ziphra.core.model.Usuario;



//@Service
public class UserUtilService {


	private Usuario usuarioSystem;
	@Autowired @Lazy
	private RepositoryFacade repos;
	
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
		Optional<Usuario> uSystemO = repos.usuario().findByUsername("SYSTEM");
		
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


	
	public Usuario getUsuarioById(String idUsuario) throws ValidationException {
		return getUsuarioById(Long.parseLong(idUsuario));
	}
	public Usuario getUsuarioById(Long idUsuario) throws ValidationException {
		Optional<Usuario> usuario = repos.usuario().findById(idUsuario);
		
		if ( usuario == null || !usuario.isPresent()) {
			throw new ValidationException(ExceptionReturnCode.USER_NOT_EXISTS); 
		}
		return usuario.get();
	}
	
	public GrupoRolesEnum getRoleForGrupo(Usuario usuario, Grupo grupo) throws ValidationException {
	    
		Optional<UserForGrupo> ufg = repos.userForGrupo().findById(new UserForGrupoId(usuario, grupo));
		
		if (!ufg.isPresent() ) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);				
		}
		
		return ufg.get().getRole();
	} 
	
	public Usuario getUsuarioForUsername(String usuario) throws ValidationException {
		
		Optional<Usuario> u = repos.usuario().findByUsername(usuario);
		
		if (!u.isPresent() ) {
			throw new ValidationException(ExceptionReturnCode.USER_NOT_EXISTS);				
		}
		 
		return u.get();
	} 
}