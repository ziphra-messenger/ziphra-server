package ar.ziphra.server.component.encryptkeys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.EncryptKeysDTO;
import ar.ziphra.common.dto.request.PublicKeyByInvitationCodeRequestDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.EncryptKeys;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

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
