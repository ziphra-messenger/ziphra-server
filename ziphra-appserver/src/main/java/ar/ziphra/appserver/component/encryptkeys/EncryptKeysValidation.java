package ar.ziphra.appserver.component.encryptkeys;

import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.dto.EncryptKeysDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EncryptKeysValidation {

	public void encryptKeysDTO(EncryptKeysDTO e) throws ValidationException {
		if (e == null) {
			log.error(ExceptionReturnCode.ENCRYPT_IS_NULL.getToShow());
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_IS_NULL);
			
		}
		
		if (e.getPrivateKey() ==null || e.getPrivateKey().trim().length() == 0) {
			log.error(ExceptionReturnCode.ENCRYPT_PRIVATE_KEY_IS_NULL.getToShow());
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_PRIVATE_KEY_IS_NULL);
			
		}
		
		if (e.getPublicKey() ==null|| e.getPublicKey().trim().length() == 0) {
			log.error(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_IS_NULL.getToShow());
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_IS_NULL);
		}
		
		if (e.getPublicKeyNoEncrypt() ==null || e.getPublicKeyNoEncrypt().trim().length() == 0) {
			log.error(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_NO_ENCRIPT_IS_NULL.getToShow());
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_NO_ENCRIPT_IS_NULL);
		}
	}
	
	public void encryptKeysInvitationCodeDTO(EncryptKeysDTO e) throws ValidationException {
		if (e == null) {
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_IS_NULL);			
		}
		
		if (e.getPrivateKey() ==null || e.getPrivateKey().trim().length() == 0) {
			log.error(ExceptionReturnCode.ENCRYPT_PRIVATE_KEY_IS_NULL.toShow());
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_PRIVATE_KEY_IS_NULL);
		}
		
		if (e.getPublicKey() !=null) {
			log.error(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_INVITATION_MUST_BE_NULL.toShow());
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_INVITATION_MUST_BE_NULL);
		}
		
		if (e.getPublicKeyNoEncrypt() ==null || e.getPublicKeyNoEncrypt().trim().length() == 0) {
			log.error(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_NO_ENCRIPT_IS_NULL.toShow());
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_PUBLIC_KEY_NO_ENCRIPT_IS_NULL);
		}
	}
	
	public void aesValitadation(AESDTO aesdto) throws ValidationException {
		if (aesdto == null) {
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_AES_IS_NULL);			
		}
		
		if (aesdto.getSecretKeyAES() == null ||  aesdto.getSecretKeyAES().toString().trim().length() == 0) {
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_AES_SECRET_KEY_IS_NULL);
		}
		
		if (aesdto.getSaltAES() ==null ||  aesdto.getSaltAES().toString().trim().length() == 0) {
			throw new ValidationException(ExceptionReturnCode.ENCRYPT_AES_SALT_IS_NULL);
		}
	}
	
}
