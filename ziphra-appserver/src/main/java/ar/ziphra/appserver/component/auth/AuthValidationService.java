package ar.ziphra.appserver.component.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import ar.ziphra.common.config.ConstantValidation;
import ar.ziphra.common.dto.request.LoginRequestDTO;
import ar.ziphra.common.dto.request.RegisterUserRequestDTO;
import ar.ziphra.common.dto.request.ValidateUsernameDTO;
import ar.ziphra.common.dto.response.LoginDTOResponse;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.enumeration.RolesSecurityAccessToServerEnum;
import ar.ziphra.core.model.EncryptKeys;
import ar.ziphra.core.model.UserInvitationCode;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import ar.ziphra.appserver.component.encryptkeys.EncryptKeysValidation;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthValidationService {

	public final static String METHOD_ACTION_AUTH_LOGIN ="login"; 
	public final static String METHOD_ACTION_AUTH_REGISTER ="registerUser";
	public final static String METHOD_ACTION_AUTH_VALIDATE_USERNAME ="validateUsername";
	
	@Autowired
	private AuthProcesor authProcesor;
	
	@Autowired
	private EncryptKeysValidation encryptKeysValidation;

	@Autowired @Lazy
	private FacadeComponent comps;


	public Boolean validateUsername(ValidateUsernameDTO request) throws ValidationException {
		String username = request.getUsername();
		
		return validateUsername(username);
		
	}
	
	public Boolean validateUsername(String username) throws ValidationException {
		
		if ( username == null) {
			log.error(ExceptionReturnCode.AUTH_USERNAME_IS_NULL.getToShow(username));
			throw new ValidationException(ExceptionReturnCode.AUTH_USERNAME_IS_NULL);
		}

		if ( username.length() < 3) {
			log.error(ExceptionReturnCode.AUTH_USERNAME_IS_TOO_SHORT.getToShow(username));
			throw new ValidationException(ExceptionReturnCode.AUTH_USERNAME_IS_TOO_SHORT);
		}
		
		
		return authProcesor.validateUsername(username);
	}
	
	public LoginDTOResponse login( LoginRequestDTO request) throws ValidationException {
		try {
			
			Usuario usuario = new Usuario(request.getUsername(),request.getPassword());
		
		
			LoginDTOResponse r = authProcesor.login(usuario);
			
				return r;
			} catch (IllegalAccessException | NoSuchFieldException | SecurityException | ValidationException
					| IOException | GeneralSecurityException e) {
				log.error(ExceptionReturnCode.GENERAL_INESPERADO.getToShow(e));
				e.printStackTrace();
				throw new ValidationException(e.getMessage());
		} catch (BadCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(ExceptionReturnCode.AUTH_BAD_CREDENTIAL.getToShow(request.toString()));
			throw new ValidationException(ExceptionReturnCode.AUTH_BAD_CREDENTIAL);
		} catch (Exception e) {
			log.error(ExceptionReturnCode.GENERAL_INESPERADO.getToShow(e));
			e.printStackTrace();
			throw new ValidationException(e.getMessage());
		}
	}
	public void registerUser(RegisterUserRequestDTO request) throws ZiphraException {
		registerUser(request, RolesSecurityAccessToServerEnum.ROLE_USER);
	}
	public void registerUser(RegisterUserRequestDTO request, RolesSecurityAccessToServerEnum erole) throws ZiphraException {
		if (validateUsername(request.getUsername())) {
			log.error(ExceptionReturnCode.AUTH_USERNAME_EXISTS.getToShow(request.toString()));
			throw new ValidationException(ExceptionReturnCode.AUTH_USERNAME_EXISTS);
		}
		Usuario usuario = new Usuario(request.getUsername(),request.getPassword());
		
		if ( request.getNickname() == null || request.getNickname().trim().equals("")) {
			
			if (request.getNickname().length() > ConstantValidation.USER_NICKNAME_MAX_LENGTH) 
			{
				log.error(ExceptionReturnCode.AUTH_USERNAME_EXISTS.getToShow(request.toString()));
				throw new ValidationException(ExceptionReturnCode.USER_NICKNAME_TOO_LONG);
			}
			
			usuario.setNickname(comps.common().randomGenerator().alias());
		}else {
			usuario.setNickname(request.getNickname());	
		}
		
		encryptKeysValidation.encryptKeysDTO(request.getEncryptKeysDTO());
		
		encryptKeysValidation.encryptKeysInvitationCodeDTO(request.getInvitationCodeEncryptKeysDTO());
		
		EncryptKeys encrypt = comps.common().mapper().doit(request.getEncryptKeysDTO());
		EncryptKeys invitationCodeEncrypt = comps.common().mapper().doit(request.getInvitationCodeEncryptKeysDTO());
		
		usuario.setEncryptKeys(encrypt);
		usuario.setUserInvitationCode(new UserInvitationCode());
		usuario.getUserInvitationCode().setEncryptKeys(invitationCodeEncrypt);
		usuario.getUserInvitationCode().setInvitationCode(comps.common().randomGenerator().invitationCode());
		log.trace("Termino de crear el Usuario nuevo");
		log.trace(usuario.toString());
		authProcesor.registerUser(usuario,erole);
	}
}
