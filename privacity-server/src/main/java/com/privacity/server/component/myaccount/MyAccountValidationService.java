package com.privacity.server.component.myaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.config.ConstantValidation;
import com.privacity.common.dto.EncryptKeysDTO;
import com.privacity.common.dto.LockDTO;
import com.privacity.common.dto.MyAccountConfDTO;
import com.privacity.common.dto.UserInvitationCodeDTO;
import com.privacity.common.dto.request.LoginRequestDTO;
import com.privacity.common.dto.request.MyAccountNicknameRequestDTO;
import com.privacity.common.dto.response.MyAccountGenerateInvitationCodeResponseDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.ValidationException;
import com.privacity.core.model.EncryptKeys;
import com.privacity.core.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.encryptkeys.EncryptKeysValidation;
import com.privacity.server.util.UtilService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyAccountValidationService {

	@Autowired
	private MyAccountProcessService myAccountService;
	
	@Autowired
	private EncryptKeysValidation encryptKeysValidation;
	@Autowired
	private UtilService utilService;
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
		
		if (request.getSeconds() == null ||
				request.getSeconds().intValue() < comps.common().serverConf().getSystemGralConf().getMyAccountConf().getLock().getMinSecondsValidation().intValue()) {
			throw new ValidationException(ExceptionReturnCode.MYACCOUNT_LOCK_MIN_SECONDS_VALIDATION);
		}
		usuarioLogged.getMyAccountConf().getLock().setSeconds(request.getSeconds());
		usuarioLogged.getMyAccountConf().getLock().setEnabled(request.isEnabled());

		myAccountService.saveMyAccountLock(usuarioLogged);
		
	}

	
	
	public void saveNickname(MyAccountNicknameRequestDTO request) throws ValidationException{
		boolean update = false;
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		if (request.getNickname() != null) {
			if (request.getNickname().length() > ConstantValidation.USER_NICKNAME_MAX_LENGTH) {
				throw new ValidationException(ExceptionReturnCode.USER_NICKNAME_TOO_LONG);
			}
			usuarioLogged.setNickname(request.getNickname());
			update=true;
		}
		
		
		if (update) myAccountService.saveNickname(usuarioLogged);
		
	}
	
	public void savePassword(LoginRequestDTO request) throws ValidationException{
		
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		
		String newPassword = comps.util().passwordEncoder().encode(request.getPassword());
		
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
