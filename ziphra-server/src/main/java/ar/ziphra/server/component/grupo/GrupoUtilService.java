package ar.ziphra.server.component.grupo;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.GrupoDTO;
import ar.ziphra.common.enumeration.ConfigurationStateEnum;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.enumeration.RulesConfEnum;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.GrupoGralConf;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GrupoUtilService {

	@Autowired @Lazy
	private FacadeComponent comps;
	
	public Long convertIdGrupoStringToLong(String idGrupo) throws ValidationException {
		Long r;
		try {
			r = Long.parseLong(idGrupo);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new ValidationException(ExceptionReturnCode.GRUPO_GRUPOID_BADFORMAT);
		}
		return r;
	}
	

	
	public Grupo getGrupoByIdValidation(GrupoDTO idGrupo) throws ValidationException {
		return getGrupoByIdValidation(idGrupo.getIdGrupo());
	}
	
	public Grupo getGrupoByIdValidation(String idGrupo) throws ValidationException {
		return getGrupoByIdValidation(Long.parseLong(idGrupo));
	}
	public Grupo getGrupoByIdValidation(Long idGrupo) throws ValidationException {
		if ( idGrupo == null) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_ID_CANT_BE_NULL);
			
		}
		Grupo g;
		try {
			g = comps.repo().grupo().findByIdGrupoAndDeleted(idGrupo, false);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new ValidationException(ExceptionReturnCode.GRUPO_GRUPOID_BADFORMAT);
		} 
		
		if (g == null) {
			// TODO Auto-generated catch block
			throw new ValidationException(ExceptionReturnCode.GRUPO_NOT_EXISTS);
		}
		
		return g;
	}
	
	public Long generateIdGrupo() {
		return Long.parseLong ((new Date().getTime()+"") + RandomStringUtils.randomNumeric(6));
	}
	
	public void validateRoleAdmin(Usuario u, Grupo g ) throws ValidationException {
		if ( g == null) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_ID_CANT_BE_NULL);
			
		}
		GrupoRolesEnum rol = comps.util().usuario().getRoleForGrupo(u, g);
		
		if ( !rol.equals(GrupoRolesEnum.ADMIN)) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_ROLE_NOT_ALLOW_THIS_ACTION);	
		}
	}
	


	public GrupoRolesEnum getGrupoRoleEnum(String role) throws ValidationException {
		GrupoRolesEnum r;
		try {
			r = GrupoRolesEnum.valueOf(role);	
		}catch (Exception e) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_ROLE_NOT_EXISTS);
		}
		return r;
		
	}
	
	public void isSameUser(Usuario usuario1, Usuario usuario2) throws ValidationException {
		if (usuario1.getIdUser().equals(usuario2.getIdUser())){
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_INVITATION_CANT_BE_THE_SAME);
		}
		
	}
	
	public void getDefaultGrupoGeneralConfiguration(Grupo g) {
		g.setGralConf(new GrupoGralConf(g));
		g.getGralConf().setAnonimo(RulesConfEnum.NULL);
		g.getGralConf().setBlockAudioMessages(false);

		g.getGralConf().setExtraEncrypt(ConfigurationStateEnum.ALLOW);
		g.getGralConf().setBlockResend(true);
		g.getGralConf().setAudiochatMaxTime(300);
		g.getGralConf().setBlackMessageAttachMandatory(false);
		g.getGralConf().setRandomNickname(false);
		g.getGralConf().setBlockMediaDownload(true);

		g.getGralConf().setHideMessageDetails(false);
		g.getGralConf().setHideMemberList(false);
		g.getGralConf().setHideMessageReadState(false);
		g.getGralConf().setTimeMessageMandatory(false);
		g.getGralConf().setTimeMessageMaxTimeAllow(300)
		
		;
				
		//g.setEncryptPublicKey(encrypt.getPublicKey());

				

	}

	public Grupo getGrupoByUsersForGrupo(List<UserForGrupo> usersForGrupo) throws ValidationException {

		if (usersForGrupo == null || usersForGrupo.size() == 0) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_NOT_EXISTS);
		}

		
		return usersForGrupo.get(0).getUserForGrupoId().getGrupo();// TODO Auto-generated method stub

	}
	
	



	
}

