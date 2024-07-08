package com.privacity.server.component.grupo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import com.privacity.common.enumeration.ConfigurationStateEnum;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;
import com.privacity.server.util.LocalDateAdapter;
import com.privacity.server.websocket.WsMessage;

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
		GrupoRolesEnum rol = comps.util().usuario().getRoleForGrupo(u, g);
		
		if ( !rol.equals(GrupoRolesEnum.ADMIN)) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_NOT_HAVE_PERMITION_ON_THIS_GRUPO_TO_ADD_MEMBERS);	
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
		
		g.getGralConf().setAnonimo(ConfigurationStateEnum.ALLOW);
		g.getGralConf().setAudiochat(ConfigurationStateEnum.ALLOW);
		g.getGralConf().setExtraEncrypt(ConfigurationStateEnum.ALLOW);
		g.getGralConf().setResend(true);
		g.getGralConf().setAudiochatMaxTime(300);
		g.getGralConf().setBlackMessageAttachMandatory(false);
		g.getGralConf().setChangeNicknameToNumber(false);
		g.getGralConf().setDownloadAllowImage(ConfigurationStateEnum.ALLOW);
		g.getGralConf().setDownloadAllowAudio(ConfigurationStateEnum.ALLOW);
		g.getGralConf().setDownloadAllowVideo(ConfigurationStateEnum.ALLOW);
		g.getGralConf().setHideMessageDetails(false);
		g.getGralConf().setHideMessageState(false);
		g.getGralConf().setTimeMessageMandatory(false);
		g.getGralConf().setTimeMessageMaxTimeAllow(300);
				
		//g.setEncryptPublicKey(encrypt.getPublicKey());

				

	}

	public Grupo getGrupoByUsersForGrupo(List<UserForGrupo> usersForGrupo) throws ValidationException {

		if (usersForGrupo == null || usersForGrupo.size() == 0) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_NOT_EXISTS);
		}

		
		return usersForGrupo.get(0).getUserForGrupoId().getGrupo();// TODO Auto-generated method stub

	}
	
	



	
}

