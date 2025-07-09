package ar.ziphra.appserver.component.myaccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.EncryptKeys;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.core.repository.EncryptKeysRepository;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import ar.ziphra.appserver.util.UtilService;
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

	public void saveNickname(String newNickname) throws ValidationException{
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		usuarioLogged.setNickname(newNickname);
		comps.repo().user().save(usuarioLogged);
		
	}
	
	public String invitationCodeGenerator(Usuario u, EncryptKeys encryptKeys, String code){

		


		 u.getUserInvitationCode().getEncryptKeys().setPrivateKey(encryptKeys.getPrivateKey());
		 u.getUserInvitationCode().getEncryptKeys().setPublicKey(encryptKeys.getPublicKey());
		 u.getUserInvitationCode().getEncryptKeys().setPublicKeyNoEncrypt(encryptKeys.getPublicKeyNoEncrypt());
		 
		
		 u.getUserInvitationCode().setInvitationCode(code);
		//UserInvitationCodeRepository.save(uic);
		
		//u.setUserInvitationCode(uic);

		comps.repo().user().save(u);
		
//		encryptKeysRepository.deleteById(oldEncryptKeysId);
		
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
