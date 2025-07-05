package ar.ziphra.server.component.myaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.config.ConstantValidation;
import ar.ziphra.common.dto.EncryptKeysDTO;
import ar.ziphra.common.dto.LockDTO;
import ar.ziphra.common.dto.MyAccountConfDTO;
import ar.ziphra.common.dto.UserInvitationCodeDTO;
import ar.ziphra.common.dto.request.ChangePasswordRequestDTO;
import ar.ziphra.common.dto.request.LoginRequestDTO;
import ar.ziphra.common.dto.request.MyAccountNicknameRequestDTO;
import ar.ziphra.common.dto.response.MyAccountGenerateInvitationCodeResponseDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.EncryptKeys;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.core.model.UsuarioPassword;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;
import ar.ziphra.server.component.encryptkeys.EncryptKeysValidation;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyAccountValidationService {

	@Autowired
	private MyAccountProcessService myAccountService;
	
	@Autowired
	private EncryptKeysValidation encryptKeysValidation;

	@Autowired @Lazy
	private FacadeComponent comps;
	
	
	
	public void closeSession() throws Exception{
	
		
		comps.service().usuarioSessionInfo().remove(comps.requestHelper().getUsuarioUsername());
	}
	
	public void saveLoginSkip(boolean request) throws ValidationException{
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		usuarioLogged.getMyAccountConf().setLoginSkip(request);
		//comps.service().usuarioSessionInfo().get(usuarioLogged.getUsername()).getMyAccountConf().setLoginSkip(request);
		
		myAccountService.saveMyAccountGeneralConfiguration(usuarioLogged);
		
	}
	public void saveLock(LockDTO request) throws ValidationException{
	
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		
		if (request.isEnabled()) {
			if (request.getSeconds() == 0 ||
					request.getSeconds() < comps.common().serverConf().getSystemGralConf().getMyAccountConf().getLock().getMinSecondsValidation()) {
				throw new ValidationException(ExceptionReturnCode.MYACCOUNT_LOCK_MIN_SECONDS_VALIDATION);
			}
		}
		usuarioLogged.getMyAccountConf().getLock().setSeconds(request.getSeconds());
		usuarioLogged.getMyAccountConf().getLock().setEnabled(request.isEnabled());

		myAccountService.saveMyAccountLock(usuarioLogged);
		
	}

	
	
	public void saveNickname(MyAccountNicknameRequestDTO request) throws ValidationException{
		
		
		if (request.getNickname() != null  && !"".equals( request.getNickname().trim())) {
			if (request.getNickname().length() > ConstantValidation.USER_NICKNAME_MAX_LENGTH) {
				throw new ValidationException(ExceptionReturnCode.USER_NICKNAME_TOO_LONG);
			}
			if (request.getNickname().length() < ConstantValidation.USER_NICKNAME_MIN_LENGTH) {
				throw new ValidationException(ExceptionReturnCode.USER_NICKNAME_TOO_SHORT);
			}

		}else {
			throw new ValidationException(ExceptionReturnCode.USER_NICKNAME_IS_NULL);			
		}
		
		myAccountService.saveNickname(request.getNickname());
		
	}
	
	public void savePassword(ChangePasswordRequestDTO request) throws ValidationException{
		
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		
		String oldPassword= comps.util().passwordEncoder().encode(request.getOldPassword());
		String newPassword= comps.util().passwordEncoder().encode(request.getNewPassword());
//		String newPasswordConfirm = comps.util().passwordEncoder().encode(request.getNewPasswordConfirm());
		String username = request.getUsername();
				
		if (username == null || username.trim().equals("")) {
			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__USERNAME__IS_NULL);
		}
		
		if (!username.equals(usuarioLogged.getUsername())) {
			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__USERNAME__INCORRECT);
		}

		if (oldPassword == null || oldPassword.trim().equals("")) {
			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__OLD_PASSWORD__IS_NULL);
		}

		
		if (newPassword == null || newPassword.trim().equals("")) {
			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__NEW_PASSWORD__IS_NULL);
		}

		if (request.getNewPasswordConfirm() == null || request.getNewPasswordConfirm().trim().equals("")) {
			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__NEW_PASSWORD_CONFIRM__IS_NULL);
		}
		
		if (oldPassword.equals(newPassword)) {
			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__NEW_PASSWORD__IS_EQUAL__OLD_PASSWORD);
		}
		if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__NEW_PASSWORD__IS_DIFERENT_NEW_PASSWORD_CONFIRM);
		}
		
//		if (!oldPassword.equals(usuarioLogged.getUsuarioPassword().getPassword())) {
//			throw new ValidationException(ExceptionReturnCode.CHANGE_PASSWORD__VALIDATION__OLD_PASSWORD__IS_DIFERENT_TO_REGISTRED__PASSWORD);
//		}
		
		usuarioLogged.getUsuarioPassword().setPassword(newPassword);
		
		myAccountService.savePassword(usuarioLogged);
		
	}
	
	public void saveMessageConf(MyAccountConfDTO request) throws ValidationException {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		
		comps.common().mapper().doit(usuarioLogged, request);
		myAccountService.saveMyAccountGeneralConfiguration(usuarioLogged);
		
	}
	public MyAccountGenerateInvitationCodeResponseDTO invitationCodeGenerator(EncryptKeysDTO request) throws ValidationException{
		
		encryptKeysValidation.encryptKeysInvitationCodeDTO(request);
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		
		EncryptKeys encryptKeys = comps.common().mapper().doit(request);
		
		return new MyAccountGenerateInvitationCodeResponseDTO(myAccountService.invitationCodeGenerator(usuarioLogged,encryptKeys,comps.common().randomGenerator().invitationCode()));
	}
	
	public Boolean isInvitationCodeAvailable(String invitationCode) throws ValidationException{
		
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Usuario UserInvitationCode = comps.repo().user().findByUserInvitationCode(invitationCode);
		
		if (UserInvitationCode == null ) return true;
		
		if (usuarioLogged.getIdUser().equals(UserInvitationCode.getIdUser())) return true;
		
		return false;
		
	}
	
	public MyAccountGenerateInvitationCodeResponseDTO saveCodeAvailable(UserInvitationCodeDTO request) throws ValidationException{
		
		if (request.getInvitationCode() == null || request.getInvitationCode().equals("")) {
			throw new ValidationException(ExceptionReturnCode.MYACCOUNT_INVITATION_CODE_CANT_BE_EMPTY);
		}
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Usuario UserInvitationCode = comps.repo().user().findByUserInvitationCode(request.getInvitationCode());
		

		if ( UserInvitationCode != null) {
			if (usuarioLogged.getIdUser().equals(UserInvitationCode.getIdUser())) {
				return new MyAccountGenerateInvitationCodeResponseDTO(request.getInvitationCode());
			}else {
				throw new ValidationException(ExceptionReturnCode.MYACCOUNT_INVITATION_CODE_NOT_AVAIBLE);		
			}
		}
		
		encryptKeysValidation.encryptKeysInvitationCodeDTO(request.getEncryptKeysDTO());
		
		EncryptKeys encryptKeys = comps.common().mapper().doit(request.getEncryptKeysDTO());
		
		return new MyAccountGenerateInvitationCodeResponseDTO(myAccountService.invitationCodeGenerator(usuarioLogged,encryptKeys,request.getInvitationCode()));

		
			
	}
}
