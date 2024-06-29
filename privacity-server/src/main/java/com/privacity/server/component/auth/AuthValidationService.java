package com.privacity.server.component.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.privacity.common.config.ConstantValidation;
import com.privacity.common.dto.request.LoginRequestDTO;
import com.privacity.common.dto.request.RegisterUserRequestDTO;
import com.privacity.common.dto.request.ValidateUsernameDTO;
import com.privacity.common.dto.response.LoginDTOResponse;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.encryptkeys.EncryptKeysValidation;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.EncryptKeys;
import com.privacity.server.model.UserInvitationCode;
import com.privacity.server.security.Usuario;

@Service
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
			throw new ValidationException(ExceptionReturnCode.AUTH_USERNAME_IS_NULL);
		}

		if ( username.length() < 3) {
			throw new ValidationException(ExceptionReturnCode.AUTH_USERNAME_IS_TOO_SHORT);
		}
		
		
		return authProcesor.validateUsername(username);
	}
	
	public LoginDTOResponse login( LoginRequestDTO request) throws ValidationException {
		try {
			
			Usuario usuario = new Usuario(request.getUsername(),request.getPassword());
		
		

				return authProcesor.login(usuario);
			} catch (IllegalAccessException | NoSuchFieldException | SecurityException | ValidationException
					| IOException | GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ValidationException(e.getMessage());
		} catch (BadCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ValidationException(ExceptionReturnCode.AUTH_BAD_CREDENTIAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ValidationException(e.getMessage());
		}
	}
	public void registerUser(RegisterUserRequestDTO request) throws ValidationException {
		if (validateUsername(request.getUsername())) {
			throw new ValidationException(ExceptionReturnCode.AUTH_USERNAME_EXISTS);
		}
		Usuario usuario = new Usuario(request.getUsername(),request.getPassword());
		
		if ( request.getNickname() == null || request.getNickname().trim().equals("")) {
			
			if (request.getNickname().length() > ConstantValidation.USER_NICKNAME_MAX_LENGTH) {
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
		authProcesor.registerUser(usuario);
	}

}
