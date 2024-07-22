package com.privacity.server.component.myaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.core.model.EncryptKeys;
import com.privacity.core.model.UserInvitationCode;
import com.privacity.core.model.Usuario;
import com.privacity.core.repository.EncryptKeysRepository;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.util.UtilService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MyAccountProcessService {


	@Autowired @Lazy
	private FacadeComponent comps;
	
	@Autowired @Lazy
	private EncryptKeysRepository encryptKeysRepository;
	//UserInvitationCodeRepository UserInvitationCodeRepository;


	@Autowired
	private UtilService utilService;

	public void saveNickname(Usuario u){

		comps.repo().user().save(u);
		
	}
	
	public String invitationCodeGenerator(Usuario u, EncryptKeys encryptKeys, String code){

		
		UserInvitationCode uic;

		long oldEncryptKeysId = u.getUserInvitationCode().getEncryptKeys().getId();
		
		uic = u.getUserInvitationCode();
		
		uic.setEncryptKeys(encryptKeys);
		uic.setInvitationCode(code);
		//UserInvitationCodeRepository.save(uic);
		
		u.setUserInvitationCode(uic);

		comps.repo().user().save(u);
		
		encryptKeysRepository.deleteById(oldEncryptKeysId);
		
		return code;
	}

	public void saveMyAccountGeneralConfiguration(Usuario usuarioLogged) {
		comps.repo().user().save(usuarioLogged);
	}
	public void saveMyAccountLock(Usuario usuarioLogged) {
		comps.repo().user().save(usuarioLogged);
	}
	public void savePassword(Usuario usuarioLogged) {
		comps.repo().user().save(usuarioLogged);
	}  
	


}
