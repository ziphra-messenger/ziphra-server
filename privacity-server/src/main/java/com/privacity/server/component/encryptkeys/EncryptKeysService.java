package com.privacity.server.component.encryptkeys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.EncryptKeysDTO;
import com.privacity.common.dto.request.PublicKeyByInvitationCodeRequestDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.EncryptKeys;
import com.privacity.server.model.Grupo;
import com.privacity.server.security.Usuario;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class EncryptKeysService {

	@Autowired @Lazy
	private FacadeComponent comps;
	
	public EncryptKeysDTO getPublicKeyByCodigoInvitacion(PublicKeyByInvitationCodeRequestDTO request) throws ValidationException {
	
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		
		Grupo g = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());

		GrupoRolesEnum rol = comps.util().usuario().getRoleForGrupo(usuarioLogged, g);
		
		if ( !rol.equals(GrupoRolesEnum.ADMIN)) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_NOT_HAVE_PERMITION_ON_THIS_GRUPO_TO_ADD_MEMBERS);	
		}
		
		
		if ( request.getInvitationCode() == null || request.getInvitationCode().trim().length() == 0) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_INVITATION_CODE_IS_NULL); 
		}

		
		Usuario UserInvitationCode = comps.repo().user().findByUserInvitationCode(request.getInvitationCode());

		if ( UserInvitationCode == null) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_NOT_EXISTS_INVITATION_CODE); 
		}
		
		 EncryptKeys e = UserInvitationCode.getEncryptKeys();
		 
		 EncryptKeysDTO r = comps.common().mapper().doitPublicKeyNoEncrypt(e);
				
		return r;
			 
		 }

}
