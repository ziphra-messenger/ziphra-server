package ar.ziphra.appserver.component.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.common.interfaces.IdGrupoInterface;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.GrupoUserConf;
import ar.ziphra.core.model.GrupoUserConfId;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.core.repository.UserForGrupoRepository;
import ar.ziphra.core.repository.UsuarioRepository;
import ar.ziphra.security.util.UserDetailsImpl;
import ar.ziphra.appserver.component.grupo.GrupoUtilService;
import ar.ziphra.appserver.component.grupouserconf.GrupoUserConfRepository;
import ar.ziphra.appserver.factory.IdsGeneratorFactory;
import lombok.extern.slf4j.Slf4j;

@RequestScope
@Component
@Slf4j
public class RequestHelperService {

	private Usuario usuario=null;
	private Grupo grupoInUse=null;
	private UserForGrupo userForGrupoInUse=null;
	private List<UserForGrupo> usersForGrupoInUse= null;;

	@Autowired @Lazy
	private GrupoUtilService grupoUtilService;
	@Autowired @Lazy
	private UserForGrupoRepository userForGrupoRepository;
	@Autowired @Lazy
	private UsuarioRepository usuarioRepository;
	
	@Autowired @Lazy
	private GrupoUserConfRepository grupoUserConfRepository;
	
	private Map<Long, String> ramdonNicknameGrupoInUse;
	@Autowired @Lazy
	IdsGeneratorFactory f;
	public String getRandomNicknameByGrupo(Long idGrupo,Long idUsuario) throws ZiphraException {
		if  (ramdonNicknameGrupoInUse == null) {
				ramdonNicknameGrupoInUse=  f.getRandomNicknameByGrupo(idGrupo+"");
		}
			return ramdonNicknameGrupoInUse.get(idUsuario+"");
	}
	
	private Map<Long, Usuario> mapUsuarioByIdUsuario = new HashMap<Long, Usuario>();
	private Map<String, Usuario> mapUsuarioByUsername = new HashMap<String, Usuario>();
	private GrupoUserConf grupoUserConfInUse;

	public Grupo setGrupoInUse( IdGrupoInterface grupoI) throws ValidationException {
		if (grupoI.getIdGrupo() == null) return null;
		return setGrupoInUse(Long.parseLong(grupoI.getIdGrupo()));
	}
	public GrupoUserConf getGrupoUserConfInUse() throws ValidationException {
		if (grupoUserConfInUse == null) {
			grupoUserConfInUse  = grupoUserConfRepository.findById(new GrupoUserConfId(getUsuarioLogged(), getGrupoInUse())).get();
		}
		return grupoUserConfInUse; 
	}
	public Grupo setGrupoInUse(String idGrupo) throws ValidationException {
		if (idGrupo == null) return null;
		return setGrupoInUse(Long.parseLong(idGrupo));
	}
	public Grupo setGrupoInUse(Long idGrupo) throws ValidationException {
		if (idGrupo == null) return null;
		if ( grupoInUse == null ) {
			grupoInUse = grupoUtilService.getGrupoByIdValidation(idGrupo);
		}
		return grupoInUse;
	}


	public Grupo getGrupoInUse() {
		return grupoInUse;
	}

	public void validationUserInTheGrupo() throws ValidationException {
		
		if ( getUserForGrupoInUse() == null ) {
			log.error(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO.toShow(" usuario: " + getUsuarioId() + " grupo: " + getGrupoInUse().getIdGrupo()));
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);	
		}
	}
	
	public UserForGrupo getUserForGrupoInUse() throws ValidationException {

		if (userForGrupoInUse==null) {
			userForGrupoInUse = userForGrupoRepository.findByIdPrimitive(getGrupoInUse().getIdGrupo(), getUsuarioId() );
		}
		return userForGrupoInUse;
	}
	






	private List<UserForGrupo> getUsersForGrupoInUse() throws ValidationException {

		if (usersForGrupoInUse==null) {
			usersForGrupoInUse = userForGrupoRepository.findByForGrupo(getGrupoInUse().getIdGrupo());	
		}
		return usersForGrupoInUse;

	}

	public List<Usuario> getUsersForGrupoInUse(boolean minusCreator, boolean onlyOnline) throws ValidationException {

		List<Usuario> r = new ArrayList<Usuario>();
		for ( UserForGrupo ufg : getUsersForGrupoInUse()) {
			
			Usuario usuarioFor=null;
			if ( getUsuarioByMap(ufg.getUserForGrupoId().getUser().getIdUser()) == null ) {
				putUsuarioInMap(ufg.getUserForGrupoId().getUser());
			}
			usuarioFor= getUsuarioByMap(ufg.getUserForGrupoId().getUser().getIdUser());
			
			boolean isCreator=false;
			if (getUsuarioLogged().getIdUser().equals(usuarioFor.getIdUser())){
				isCreator=true;
			}

//			boolean isOnlyLine=false;	
//			if (onlyOnline) {
//				Set<String> online = socketSessionRegistry.getSessionIds(usuarioFor.getUsername());
//				if (!online.isEmpty() ) {
//					isOnlyLine=true;
//				}
//			}
//
			boolean add=true;
			
			if ( minusCreator && isCreator ) {
				add=false;
			}
			
//			if ( onlyOnline && !isOnlyLine ) {
//				add=false;
//			}
//			add=true;
			if (add) r.add(ufg.getUserForGrupoId().getUser());

		}
		return r;

	}
	public List<Usuario> getUserNamesForGrupoInUseMinusLoggedUser() throws ValidationException {
		
		return getUsersForGrupoInUse(true,false);
	}
	public List<Usuario> getUserNamesForGrupoInUseOnlyOnline() throws ValidationException {
		
		return getUsersForGrupoInUse(false,true);
	}
	public List<Usuario> getUserNamesForGrupoInUseOnlyOnlineMinusCreator() throws ValidationException {
		
		return getUsersForGrupoInUse(true,true);
	}
	public List<Usuario> getAllUserForGrupo() throws ValidationException {
		
		return getUsersForGrupoInUse(false,false);
	}
	
	public boolean isUsuarioLoggedAdminOnGrupoInUse() throws ValidationException {
		return getUserForGrupoInUse().getRole().equals(GrupoRolesEnum.ADMIN);
	}
	
	public Usuario getUsuarioLogged() throws ValidationException {
		if (usuario == null) {
			usuario = getUsuarioLoggedValidate();	
			mapUsuarioByIdUsuario.put(usuario.getIdUser(), usuario);
			mapUsuarioByUsername.put(usuario.getUsername(), usuario);
		}
		return usuario;
	}

	private void putUsuarioInMap(Usuario usuario) {
		mapUsuarioByIdUsuario.put(usuario.getIdUser(), usuario);
		mapUsuarioByUsername.put(usuario.getUsername(), usuario);
		
	}
	private Usuario getUsuarioByMap(Long idUser) {
		return mapUsuarioByIdUsuario.get(usuario.getIdUser());
	}

	public String getUsuarioUsername() throws ValidationException {
		return this.getUsuarioLogged().getUsername();
	}

	public Long getUsuarioId() throws ValidationException {
		return this.getUsuarioLogged().getIdUser();
	}
	
	private Usuario getUsuarioLoggedValidate() throws ValidationException {
    
	Usuario u = getUsuarioLoggedRequest();
	
	if ( u.getIdUser() == null) {
		ValidationException e = new ValidationException(ExceptionReturnCode.USER_USER_NOT_LOGGER);
		
		throw e; 
	}
	return u;
	}
	
//	public Usuario setUsuarioLoggedRequest(String username) {
////		Authentication auth = SecurityContextHolder
////	            .getContext()
////	            .getAuthentication();
////	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
//	    
//		Usuario u = this.usuarioRepository.findByUsername(username).get();
//		usuario=u;
//		return usuario;
//	}    
	
	public String getUsuarioLoggedToken() {


		Authentication auth = SecurityContextHolder
		        .getContext()
		        .getAuthentication();
		UserDetailsImpl userDetail = (UserDetailsImpl) auth.getPrincipal();
		
		return userDetail.getJwt();

	
}    
	
	private Usuario getUsuarioLoggedRequest() {


			Authentication auth = SecurityContextHolder
			        .getContext()
			        .getAuthentication();
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			
			Usuario u = this.usuarioRepository.findByUsername(userDetail.getUsername()).get();
			usuario=u;

		return usuario;
	}
	public void setGrupoInUse(Grupo grupo) {
		this.grupoInUse=grupo;
		
	}    
}
